package com.lespsan543.visionplay.app.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lespsan543.visionplay.app.data.model.CommentModel
import com.lespsan543.visionplay.app.data.model.UserModel
import com.lespsan543.visionplay.app.domain.DiscoverMoviesUseCase
import com.lespsan543.visionplay.app.domain.DiscoverSeriesUseCase
import com.lespsan543.visionplay.app.domain.DiscoverSimilarMoviesUseCase
import com.lespsan543.visionplay.app.domain.GetCinemaUseCase
import com.lespsan543.visionplay.app.domain.GetMovieGenresUseCase
import com.lespsan543.visionplay.app.domain.GetSerieGenresUseCase
import com.lespsan543.visionplay.app.domain.GetTrailerUseCase
import com.lespsan543.visionplay.app.ui.states.MovieOrSerieState
import com.lespsan543.visionplay.guardar.Property1
import com.lespsan543.visionplay.menu.PropertyBottomBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * ViewModel responsable del flujo de películas y series conectándose a la API, además
 * de añadir las películas o series a la base de datos que el usuario añada a favoritos
 *
 * @property auth Instancia de FirebaseAuth utilizada para obtener el usuario actual
 * @property firestore Instancia de FirebaseFirestore utilizada para operaciones en la base de datos
 * @property discoverMoviesUseCase caso de uso para invocar la función que busca películas de la API
 * @property discoverSeriesUseCase caso de uso para invocar la función que busca series de la API
 * @property _moviePosition flujo de datos que mantiene la posición de la película que se va a mostrar al usuario
 * @property moviePosition estado público de la posición de la película
 * @property _seriePosition flujo de datos que mantiene la posición de la serie que se va a mostrar al usuario
 * @property seriePosition estado público de la posición de la serie
 * @property _movieList flujo de datos de las películas que se han recogido en la API
 * @property movieList estado público de la lista de películas recogida
 * @property _serieList flujo de datos de las series que se han encontrado en la API
 * @property serieList estado público de la lista de series recogida
 * @property _propertyButton flujo de datos de la propiedad en la que se encuetra en botón de guardado
 * @property propertyButton estado público de la propiedad del botón de guardado
 * @property _favoritesInDB flujo de datos de las películas que se han recogido de la base de datos
 * @property _actualMovieOrSerieState flujo de datos de la película actual que se está mostrando con los datos de la base de datos
 */
class VisionPlayViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore

    private val discoverMoviesUseCase = DiscoverMoviesUseCase()

    private val discoverSeriesUseCase = DiscoverSeriesUseCase()

    private val getMovieGenresUseCase = GetMovieGenresUseCase()

    private val getSerieGenresUseCase = GetSerieGenresUseCase()

    private val getTrailerUseCase = GetTrailerUseCase()

    private val getCinemaUseCase = GetCinemaUseCase()

    private val getSimilarMoviesUseCase = DiscoverSimilarMoviesUseCase()

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var name by mutableStateOf("")
        private set

    private var _wrong = MutableStateFlow(false)
    var wrong : StateFlow<Boolean> = _wrong

    private var _moviePosition = MutableStateFlow(0)
    var moviePosition : StateFlow<Int> = _moviePosition

    private var _seriePosition = MutableStateFlow(0)
    var seriePosition : StateFlow<Int> = _seriePosition

    private var _movieList = MutableStateFlow<List<MovieOrSerieState>>(emptyList())
    var movieList : StateFlow<List<MovieOrSerieState>> = _movieList.asStateFlow()

    private var _serieList = MutableStateFlow<List<MovieOrSerieState>>(emptyList())
    var serieList : StateFlow<List<MovieOrSerieState>> = _serieList.asStateFlow()

    private var _propertyButton = MutableStateFlow(Property1.Default)
    var propertyButton : StateFlow<Property1> = _propertyButton

    private var _favoritesInDB = MutableStateFlow<List<MovieOrSerieState>>(emptyList())
    val favoritesInDB : StateFlow<List<MovieOrSerieState>> = _favoritesInDB

    private var _moviesAndSeriesByGenreList = MutableStateFlow<List<MovieOrSerieState>>(emptyList())
    val moviesAndSeriesByGenreList : StateFlow<List<MovieOrSerieState>> = _moviesAndSeriesByGenreList

    private var _dbList = MutableStateFlow<List<List<MovieOrSerieState>>>(emptyList())

    private var _actualMovieOrSerieState = MutableStateFlow(MovieOrSerieState())

    private var _movieGenres = MutableStateFlow<Map<String,String>>(emptyMap())

    private var _serieGenres = MutableStateFlow<Map<String,String>>(emptyMap())

    private var _genres = MutableStateFlow<Map<String,String>>(emptyMap())

    private var _showGenres = MutableStateFlow("")
    var showGenres : StateFlow<String> = _showGenres

    private var _trailerId = MutableStateFlow("")
    var trailerId : StateFlow<String> = _trailerId

    private var _commentsList = MutableStateFlow<List<CommentModel>>(emptyList())
    var commentsList : StateFlow<List<CommentModel>> = _commentsList

    private var _userName = MutableStateFlow("")
    var userName : StateFlow<String> = _userName

    var commentText = mutableStateOf("")

    private var _similarMovies = MutableStateFlow<List<MovieOrSerieState>>(emptyList())
    var similarMovies : StateFlow<List<MovieOrSerieState>> = _similarMovies

    private val _selectedMovieOrSerie = MutableStateFlow(MovieOrSerieState())
    var selectedMovieOrSerie : StateFlow<MovieOrSerieState> = _selectedMovieOrSerie

    private val _lastSelectedMoviesOrSeries = MutableStateFlow<List<MovieOrSerieState>>(emptyList())

    private val _cinemaList = MutableStateFlow<List<MovieOrSerieState>>(emptyList())
    val cinemaList : StateFlow<List<MovieOrSerieState>> = _cinemaList

    private val _showTrailer = MutableStateFlow(false)
    val showTrailer : StateFlow<Boolean> = _showTrailer

    private var _actualGenre = MutableStateFlow("")

    private var _searchByGenrePosition = MutableStateFlow(0)
    var searchByGenrePosition : StateFlow<Int> = _searchByGenrePosition

    var genresToShow = listOf("Crime","Comedy","Animation","Action","Adventure", "Fantasy","Horror","Romance","Mystery","Western")

    private var _propertyBottomBar = MutableStateFlow(PropertyBottomBar.Inicio)
    var propertyBottomBar : StateFlow<PropertyBottomBar> = _propertyBottomBar

    var userSearch by mutableStateOf("")
        private set

    init {
        //Hacemos una primera búsqueda de películas y series al iniciar la aplicación
        fetchMoviesFromDB()
        fetchSeriesFromDB()
        movieGenres()
        serieGenres()
        findUserInDB()
        getCinemaMovies()
    }

    fun changeBottomBar(property : PropertyBottomBar){
        _propertyBottomBar.value = property
    }

    /**
     * Muestra una nueva película o serie
     */
    fun newMovieOrSerie(){
        _propertyButton.value = Property1.Default
        if (_searchByGenrePosition.value == _dbList.value.size-1){
            _searchByGenrePosition.value=0
        }else{
            _searchByGenrePosition.value++
        }
    }

    /**
     * Muestra la película o serie anterior
     */
    fun lastMovieOrSerie(){
        _propertyButton.value = Property1.Default
        if (_searchByGenrePosition.value == 0){
            _searchByGenrePosition.value=0
        }else{
            _searchByGenrePosition.value--
        }
    }

    /**
     * Guarda la película o serie que se ha seleccionado en la variable
     * del ViewModel
     *
     * @param movieOrSerie película o serie que se ha seleccioando
     */
    fun changeFavoriteSelected(movieOrSerie:MovieOrSerieState){
        _selectedMovieOrSerie.value = movieOrSerie
    }

    /**
     * Busca todas las películas y series de la base de datos a partir del género seleccionado
     */
    private fun fetchMoviesAndSeriesFromDBByGenre() {
        firestore.collection("MoviesAndSeries")
            .whereArrayContains("genres", _actualGenre.value)
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val moviesAndSeries = mutableListOf<MovieOrSerieState>()
                if (querySnapshot != null) {
                    for (document in querySnapshot) {
                        val movieOrSerie = document.toObject(MovieOrSerieState::class.java)
                        movieOrSerie.idDoc = document.id
                        moviesAndSeries.add(movieOrSerie)
                    }
                }
                _moviesAndSeriesByGenreList.value = moviesAndSeries
            }
    }

    /**
     * Buscamos una lista de películas en la API
     */
    private fun getAllMovies(page:Int){
        viewModelScope.launch(Dispatchers.IO) {
            _movieList.value = discoverMoviesUseCase.invoke(page).results
        }
    }

    /**
     * Buscamos una lista de series en la API
     */
    private fun getAllSeries(page:Int){
        viewModelScope.launch(Dispatchers.IO) {
            _serieList.value = discoverSeriesUseCase.invoke(page).results
        }
    }

    /**
     * Buscamos una lista de películas que se encuentran actualmente en el cine en la API
     */
    private fun getCinemaMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            _cinemaList.value = getCinemaUseCase.invoke(1).results
        }
    }

    /**
     * Muestra en la pantalla el trailer de la película seleccionada o cierra el diálogo
     */
    fun showMovieTrailer(){
        _showTrailer.value = !_showTrailer.value
    }

    /**
     * Obtiene el nombre de los géneros de la película o serie que se indica
     *
     * @param movieOrSerie película o serie de la que vamos a obtener los géneros
     */
    fun getGenresToShow(movieOrSerie: MovieOrSerieState){
        var genres = ""
        for (i in movieOrSerie.genres){
            var genre = ""
            if (i in _movieGenres.value.keys){
                genre = _movieGenres.value.get(i).toString()
            }
            else if (i in _serieGenres.value.keys){
                genre = _serieGenres.value.get(i).toString()
            }
            genres+=genre+"\n"
        }
        _showGenres.value = genres
    }

    /**
     * Cierra la sesión del usuario
     */
    fun signOut() {
        auth.signOut()
    }

    /**
     * Realiza una búsqueda amplia de películas y series en la API para introducirlas en la base de datos de Firebase
     */
    private fun loadFromAPI(){
        val temporalList = mutableListOf<List<MovieOrSerieState>>()
        for (z in 1..20-1){
            getAllSeries(z)
            getAllMovies(z)
            Thread.sleep(500)
            temporalList.add(_serieList.value)
            temporalList.add(_movieList.value)
        }
        _dbList.value = temporalList
        saveInDB()
    }

    /**
     * Guarda todas las películas y series obtenidas en la base de datos
     */
    private fun saveInDB(){
        for (list in _dbList.value){
            for (movieOrSerie in list){
                saveMovieOrSerie(movieOrSerie)
            }
        }
    }

    /**
     * Borra todas las películas y series que están guardadas en la base de datos para actualizarlas
     */
    fun restartDB(){
        for (list in _dbList.value){
            for (movieOrSerie in list){
                try {
                    viewModelScope.launch {
                        firestore.collection("MoviesAndSeries").document(movieOrSerie.idDoc)
                            .delete()
                    }
                }catch (e: Exception) {
                    print(e.localizedMessage)
                }
            }
        }
        loadFromAPI()
    }

    /**
     * Buscamos los géneros que hay entre películas y series
     * para que no se repitan si alguno coincide
     * y los guardamos en la variable _genres
     */
    fun diferentGenres(genre:String){
        val temporalGenresMap = mutableMapOf<String, String>()
        if (_genres.value.isEmpty()){
            for ((key, value) in _movieGenres.value){
                temporalGenresMap[key] = value
            }
            for ((key, value) in _serieGenres.value){
                if (key !in _genres.value.keys){
                    temporalGenresMap[key] = value
                }
            }
            _genres.value = temporalGenresMap
        }
        getActualGenre(genre)
    }

    /**
     * Guarda la clave del género que quiere buscar el usuario
     *
     * @param genre género a buscar
     */
    private fun getActualGenre(genre:String){
        for ((key, value ) in _genres.value){
            if (genre == value){
                _actualGenre.value = key
                break
            }
        }
        fetchMoviesAndSeriesFromDBByGenre()
    }

    /**
     * Comprueba si el usuario que ha iniciado sesión es administrador para que tenga permisos
     * sobre la base de datos y pueda actualizar la información de la misma
     *
     * @return booleano dependiendo de si el usuario que ha iniciado sesión es administrador o no
     */
    fun isAdmin(): Boolean {
        val email = auth.currentUser?.email
        return email == "admin@admin.com"
    }

    /**
     * Guarda la película o serie que se ha seleccionado en la variable
     * del ViewModel
     */
    fun changeSelectedMovieOrSerie(){
        val temporalList = _lastSelectedMoviesOrSeries.value.toMutableList()
        temporalList.removeAt(_lastSelectedMoviesOrSeries.value.size-1)
        _lastSelectedMoviesOrSeries.value = temporalList
        if (_lastSelectedMoviesOrSeries.value.isNotEmpty()){
            _selectedMovieOrSerie.value = _lastSelectedMoviesOrSeries.value.last()
        }
    }

    /**
     * Guarda la película o serie que se ha seleccionado anteriormente en la lista
     *
     * @param movieOrSerie película o serie que se ha seleccionado
     */
    fun addSelected(movieOrSerie:MovieOrSerieState){
        val temporalList = _lastSelectedMoviesOrSeries.value.toMutableList()
        temporalList.add(movieOrSerie)
        _lastSelectedMoviesOrSeries.value = temporalList
        _selectedMovieOrSerie.value = movieOrSerie
    }

    /**
     * Buscamos los distintos géneros de las películas
     */
    private fun movieGenres(){
        viewModelScope.launch(Dispatchers.IO) {
            _movieGenres.value = getMovieGenresUseCase.invoke()
        }
    }

    /**
     * Buscamos los distintos géneros de las series
     */
    private fun serieGenres(){
        viewModelScope.launch(Dispatchers.IO) {
            _serieGenres.value = getSerieGenresUseCase.invoke()
        }
    }

    fun findSimilarMovies(movie: MovieOrSerieState){
        viewModelScope.launch {
            _similarMovies.value = getSimilarMoviesUseCase.invoke(movie.idAPI).results
        }
    }

    /**
     * Comprueba si el nombre de la película ya se encuentra en la base de datos
     * para mostrar el botón de guardado correspondiente
     *
     * @param title título de la película o serie que queremos comprobar
     */
    fun findMovieInList(title: String){
        for (movie in _favoritesInDB.value) {
            if (title == movie.title){
                _propertyButton.value = Property1.Guardado
                _actualMovieOrSerieState.value = movie
            }
        }
    }

    /**
     * Guarda el comentario del usuario en la base de datos
     *
     * @param movieOrSerie película o serie a la que realiza el comentario
     * @param comment comentario del usuario
     */
    fun saveComment(movieOrSerie: String, comment : String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val commentDB = hashMapOf(
                    "movieOrSerie" to movieOrSerie,
                    "comment" to comment,
                    "user" to _userName.value
                )
                firestore.collection("Comments")
                    .add(commentDB)
                    .addOnFailureListener {
                        throw Exception()
                    }
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    /**
     * Busca todos los comentarios de una película o serie de la base de datos
     *
     * @param movieOrSerie película o serie de la que vamos a buscar los comentarios
     */
    fun fetchCommentsFromDB(movieOrSerie : String) {
        firestore.collection("Comments")
            .whereEqualTo("movieOrSerie", movieOrSerie)
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val comments = mutableListOf<CommentModel>()
                if (querySnapshot != null) {
                    for (document in querySnapshot) {
                        val comment = document.toObject(CommentModel::class.java)
                        comment.idDoc = document.id
                        comments.add(comment)
                    }
                }
                _commentsList.value = comments
            }
    }

    /**
     * Busca al usuario actual en la base de datos
     */
    fun findUserInDB() {
        val email = auth.currentUser?.email
        firestore.collection("Users")
            .whereEqualTo("email", email)
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    for (document in querySnapshot) {
                        val user = document.toObject(UserModel::class.java)
                        _userName.value = user.name
                    }
                }
            }
    }

    /**
     * Busca todas las películas de la base de datos
     */
    private fun fetchMoviesFromDB() {
        firestore.collection("MoviesAndSeries")
            .whereEqualTo("type", "Movie")
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val movies = mutableListOf<MovieOrSerieState>()
                if (querySnapshot != null) {
                    for (document in querySnapshot) {
                        val movie = document.toObject(MovieOrSerieState::class.java)
                        movie.idDoc = document.id
                        movies.add(movie)
                    }
                }
                _movieList.value = movies
            }
    }

    /**
     * Guarda el comentario que ha escrito el usuario
     */
    fun writeComment(new: String){
        commentText.value = new
    }

    /**
     * Reinicia el texto del comentario que ha escrito el usuario una vez lo ha publicado
     */
    fun newComment(){
        commentText.value = ""
    }

    /**
     * Busca todas las películas de la base de datos
     */
    private fun fetchSeriesFromDB() {
        firestore.collection("MoviesAndSeries")
            .whereEqualTo("type", "Serie")
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val series = mutableListOf<MovieOrSerieState>()
                if (querySnapshot != null) {
                    for (document in querySnapshot) {
                        val serie = document.toObject(MovieOrSerieState::class.java)
                        serie.idDoc = document.id
                        series.add(serie)
                    }
                }
                _serieList.value = series
            }
    }

    /**
     * Busca todas las películas y series que ya están añadidas a favoritos
     * en la base de datos
     */
    fun fetchFavoritesFromDB() {
        val email = auth.currentUser?.email
        firestore.collection("Favoritos")
            .whereEqualTo("emailUser", email.toString())
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val movies = mutableListOf<MovieOrSerieState>()
                if (querySnapshot != null) {
                    for (document in querySnapshot) {
                        val movie = document.toObject(MovieOrSerieState::class.java)
                        movie.idDoc = document.id
                        movies.add(movie)
                    }
                }
                _favoritesInDB.value = movies
            }
    }

    /**
     * Muestra una nueva película
     */
    fun newMovie(){
        _propertyButton.value = Property1.Default
        if (_moviePosition.value == _movieList.value.size-1){
            _moviePosition.value=0
        }else{
            _moviePosition.value++
        }
    }

    /**
     * Muestra la película anterior
     */
    fun lastMovie(){
        _propertyButton.value = Property1.Default
        if (_moviePosition.value == 0){
            _moviePosition.value=0
        }else if(_moviePosition.value <= 0) {
            _moviePosition.value = _movieList.value.size - 1
        }else{
            _moviePosition.value--
        }
    }

    /**
     * Muestra una nueva serie
     */
    fun newSerie(){
        _propertyButton.value = Property1.Default
        if (_seriePosition.value == _serieList.value.size-1){
            _seriePosition.value=0
        }else{
            _seriePosition.value++
        }
    }

    /**
     * Muestra la serie anterior
     */
    fun lastSerie(){
        _propertyButton.value = Property1.Default
        if (_seriePosition.value == 0){
            _seriePosition.value=0
        } else if (_seriePosition.value <= 0) {
            _seriePosition.value = _movieList.value.size - 1
        }else{
            _seriePosition.value--
        }
    }

    /**
     * Cambia la propiedad del botón de guardado dependiendo de cuando se pulsa
     */
    private fun guardarPeliculaOSerie(){
        if (_propertyButton.value == Property1.Default){
            _propertyButton.value = Property1.Guardado
        }else{
            _propertyButton.value = Property1.Default
        }
    }

    /**
     * Guarda la película o serie que se le indica en la base de datos
     *
     * @param searchMovieState película o serie que queremos añadir a favoritos
     */
    fun saveMovieOrSerie(searchMovieState: MovieOrSerieState) {
        val email = auth.currentUser?.email

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newMovieOrSerie = hashMapOf(
                    "idAPI" to searchMovieState.idAPI,
                    "title" to searchMovieState.title,
                    "overview" to searchMovieState.overview,
                    "poster" to searchMovieState.poster,
                    "date" to searchMovieState.date,
                    "votes" to searchMovieState.votes,
                    "genres" to searchMovieState.genres,
                    "emailUser" to email.toString()
                )
                firestore.collection("Favoritos")
                    .add(newMovieOrSerie)
                    .addOnSuccessListener {
                        guardarPeliculaOSerie()
                    }
                    .addOnFailureListener {
                        throw Exception()
                    }
            } catch (e: Exception){
                _propertyButton.value = Property1.Default
            }
        }
    }

    /**
     * Elimina una película o serie de la base de datos a partir de su id
     */
    fun deleteMovieOrSerie() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                firestore.collection("Favoritos").document(_actualMovieOrSerieState.value.idDoc)
                    .delete()
                    .addOnSuccessListener {
                        guardarPeliculaOSerie()
                    }
                    .addOnFailureListener {
                        _propertyButton.value = Property1.Guardado
                    }
            } catch (e:Exception) {
                _propertyButton.value = Property1.Guardado
            }
        }
    }

    /**
     * Calcula la puntuación de una película o serie del 1 al 5
     *
     * @param movieOrSerie película o serie de la que vamos a obtener la puntuación
     *
     * @return puntuación
     */
    fun calculateVotes(movieOrSerie: MovieOrSerieState) : Int{
        val votes = movieOrSerie.votes.toDouble() / 2
        return votes.roundToInt()
    }

    /**
     * Formatea el título de la película o serie para realizar la búsqueda del trailer en la API
     */
    fun formatTitle(title: String){
        val result = "official trailer "
        getTrailer(result+title)
    }

    /**
     * Busca el trailer de la película o serie en la API
     */
    private fun getTrailer(title: String){
        _trailerId.value = ""
        viewModelScope.launch(Dispatchers.IO) {
            _trailerId.value = getTrailerUseCase.invoke(title)
        }
    }

    /**
     * Resetea el identificador del trailer
     */
    fun resetTrailer(){
        _trailerId.value = ""
    }

    /**
     * Reinicia el número de página de la API y la posición al cambiar de género
     */
    fun reset(){
        _searchByGenrePosition.value = 0
    }

    /**
     * Crea al usuario para darle de alta en la autenticación
     *
     * @param onSuccess si se crea correctamente navega a la siguiente pantalla
     */
    fun createUser(onSuccess: () -> Unit){
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            saveUser(name)
                            onSuccess()
                        } else {
                            _wrong.value = true
                        }
                    }
                findUserInDB()
            } catch (e: Exception){
                _wrong.value = true
            }
        }
    }

    /**
     * Guarda un nuevo usuario en la base de datos
     *
     * @param username nombre del usuario
     */
    private fun saveUser(username: String){
        val id = auth.currentUser?.uid
        val email = auth.currentUser?.email

        viewModelScope.launch(Dispatchers.IO) {
            val user = hashMapOf(
                "id" to id.toString(),
                "email" to email.toString(),
                "name" to username,
                "password" to password
            )
            firestore.collection("Users")
                .add(user)
        }
    }

    /**
     * Permite iniciar sesión al usuario y controla que los datos introducidos corresponden con los de la base de datos
     *
     * @param onSuccess si se crea correctamente navega a la siguiente pantalla
     */
    fun logIn(onSuccess: () -> Unit){
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { log ->
                        if (log.isSuccessful) {
                            onSuccess()
                        } else {
                            _wrong.value = true
                        }
                    }
                findUserInDB()
            } catch (e: Exception){
                _wrong.value = true
            }
        }
    }

    /**
     * Resetea la información de los campos email, nombre y contraseña
     */
    fun resetLogInOrRegister(){
        email = ""
        name = ""
        password = ""
    }

    /**
     * Cambia el booleano que controla que se muestre el diálogo si algún dato en el resgistro
     * o inicio de sesión es incorrecto
     */
    fun closeDialog(){
        _wrong.value = false
    }

    /**
     * Guarda en la variable el email que escribe el usuario
     *
     * @param email email del usuario
     */
    fun writeEmail(email:String){
        this.email = email
    }

    /**
     * Guarda en la variable el nombre que escribe el usuario
     *
     * @param name nombre del usuario
     */
    fun writeName(name:String){
        this.name = name
    }

    /**
     * Guarda en la variable la contraseña que escribe el usuario
     *
     * @param password contraseña del usuario
     */
    fun writePassword(password:String){
        this.password = password
    }

    /**
     * Guarda en la variable la búsqueda que va a realizar el usuario
     *
     * @param search contraseña del usuario
     */
    fun writeSearch(search:String){
        this.userSearch = search
    }
}