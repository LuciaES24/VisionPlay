package com.lespsan543.visionplay.app.ui.viewModel

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import com.lespsan543.visionplay.app.domain.DiscoverMovieProviderUseCase
import com.lespsan543.visionplay.app.domain.DiscoverMoviesUseCase
import com.lespsan543.visionplay.app.domain.DiscoverSerieProviderUseCase
import com.lespsan543.visionplay.app.domain.DiscoverSeriesUseCase
import com.lespsan543.visionplay.app.domain.DiscoverSimilarMoviesUseCase
import com.lespsan543.visionplay.app.domain.DiscoverSimilarSeriesUseCase
import com.lespsan543.visionplay.app.domain.GetCinemaUseCase
import com.lespsan543.visionplay.app.domain.GetMovieGenresUseCase
import com.lespsan543.visionplay.app.domain.GetSerieGenresUseCase
import com.lespsan543.visionplay.app.domain.GetTrailerUseCase
import com.lespsan543.visionplay.app.domain.SearchUseCase
import com.lespsan543.visionplay.app.ui.states.MovieOrSerieProviderState
import com.lespsan543.visionplay.app.ui.states.MovieOrSerieState
import com.lespsan543.visionplay.guardar.Property1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.math.roundToInt

/**
 * ViewModel responsable del flujo de datos de la aplicación
 *
 * @property auth Instancia de FirebaseAuth utilizada para obtener el usuario actual
 * @property firestore Instancia de FirebaseFirestore utilizada para operaciones en la base de datos
 * @property discoverMoviesUseCase caso de uso para invocar la función que busca películas de la API
 * @property discoverSeriesUseCase caso de uso para invocar la función que busca series de la API
 * @property getMovieGenresUseCase caso de uso para invocar la función que busca los géneros de las películas en la API
 * @property getSerieGenresUseCase caso de uso para invocar la función que busca los géneros de las series en la API
 * @property getTrailerUseCase caso de uso para invocar la función que realiza la búsqueda de un trailer
 * @property getCinemaUseCase caso de uso para invocar la función que busca las películas que se encuentran en el cine actualmente
 * @property getSimilarMoviesUseCase caso de uso para invocar la función que busca películas y series parecidas a la película seleccionada
 * @property getSimilarSeriesUseCase caso de uso para invocar la función que busca películas y series parecidas a la serie seleccionada
 * @property discoverMovieProviderUseCase caso de uso para invocar la función que busca las plataformas en las que se encuentran las películas
 * @property discoverSerieProviderUseCase caso de uso para invocar la función que busca las platadormas en las que se encuentran las series
 * @property searchUseCase caso de uso para invocar la función que realiza la búsqueda del usuario
 * @property email variable en la que vamos a guardar el email que escriba el usuario
 * @property password variable en la que vamos a guardar la contraseña que escriba el usuario
 * @property name variable en la que vamos a guardar el nombre que escriba el usuario
 * @property _wrong flujo de datos del booleano que determina si alguno de los datos está incorrecto para mostrar el error
 * @property wrong estado público del boleano que controla los errores
 * @property _moviePosition flujo de datos que mantiene la posición de la película que se va a mostrar al usuario
 * @property moviePosition estado público de la posición de la película
 * @property _seriePosition flujo de datos que mantiene la posición de la serie que se va a mostrar al usuario
 * @property seriePosition estado público de la posición de la serie
 * @property _movieAPIList flujo de datos que mantiene la lista de películas recogidas de la API
 * @property _movieList flujo de datos de las películas que se han recogido de la base de datos
 * @property movieList estado público de la lista de películas recogida
 * @property _serieAPIList flujo de datos que mantiene la lista de series recogidas de la API
 * @property _serieList flujo de datos de las series que se han encontrado en la API
 * @property serieList estado público de la lista de series recogida
 * @property _propertyButton flujo de datos de la propiedad en la que se encuetra en botón de guardado
 * @property propertyButton estado público de la propiedad del botón de guardado
 * @property _favoritesInDB flujo de datos de las películas que se han recogido de la base de datos
 * @property favoritesInDB estado público de la lista de películas añadidas a favoritos por el usuario
 * @property _moviesAndSeriesByGenreList flujo de datos que mantiene la lista de películas y series recogidas de la base de datos filtradas por género
 * @property moviesAndSeriesByGenreList estado público de la lista de películas y géneros filtrada por género
 * @property _dbList flujo de datos que guarda todas las películas y series recogidas de la API cuando se actualiza la base de datos
 * @property _actualMovieOrSerieState flujo de datos de la película actual que se está mostrando con los datos de la base de datos
 * @property _movieGenres flujo de datos que mantiene los posibles géneros que puede tener una película buscados en la API
 * @property _serieGenres flujo de datos que mantiene los posibles géneros que puede tener una serie buscados en la API
 * @property _genres flujo de datos que mantiene los posibles géneros que existen
 * @property _showGenres flujo de datos que mantiene la cadena con los nombres de los géneros de una película o serie
 * @property showGenres estado público de la cadena con los géneros de la película o serie seleccionada
 * @property _trailerId flujo de datos que mantiene el identificador del tráiler de la película o serie seleccionada
 * @property trailerId estado público del identificador del tráiler
 * @property _commentsList flujo de datos que recoge los comentarios de una película o serie de la base de datos
 * @property commentsList estado público de la lista de comentarios
 * @property _userName flujo de datos que guarda el nombre del usuario que ha iniciado sesión
 * @property userName estado público del nombre del usuario que ha iniciado sesión
 * @property commentText variable en la que guardamos el comentario que escribe el usuario
 * @property _similarMoviesAndSeries flujo de datos que guarda la lista de similares de una película o serie recogida de la API
 * @property similarMoviesAndSeries estado público con la lista de similares
 * @property _selectedMovieOrSerie flujo de datos que guarda la película o serie seleccionada cuando navega a la pantalla de información de la misma
 * @property selectedMovieOrSerie estado público de la película o serie seleccionada
 * @property _lastSelectedMoviesOrSeries flujo de datos con la lista de películas o series que se han seleccionado
 * @property _cinemaList flujo de datos que guarda la consulta a la API con las películas que se encuentran en el cine
 * @property cinemaList estado público con las películas que se encuentran actualmente en el cine
 * @property _showTrailer flujo de datos que indica si se debe mostrar el tráiler en la pantalla de la cartelera
 * @property showTrailer estado público que indica si se muestra el tráiler
 * @property _actualGenre flujo de datos que guarda la clave del género que ha seleccionado el usuario para filtrar
 * @property _selectedGenreText flujo de datos que guarda el valor del género que ha seleccionado el usuario para filtrar
 * @property selectedGenreText estado público con el valor del género seleccionado
 * @property _searchByGenrePosition flujo de datos que mantiene la posición de la película o serie que se va a mostrar al usuario
 * @property searchByGenrePosition estado público de la posición de la película o serie
 * @property genresToShow lista de géneros que se van a mostrar al usuario y por los que podrá filtrar
 * @property _providerList flujo de datos que guarda las plataformas en las que se encuentra una película o serie
 * @property providerList estado público de la lista de plataformas
 * @property _platformLink flujo de datos con el enlace a la plataforma seleccionada
 * @property userSearch variable en la que guardamos la búsqueda del usuario
 * @property _searchList flujo de datos con los resultados de la búsqueda del usuario en la API
 * @property searchList estado público con la lista de resultados de la búsqueda
 * @property loadingDB indica si se está actualizando la base de datos
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

    private val getSimilarSeriesUseCase = DiscoverSimilarSeriesUseCase()

    private val discoverMovieProviderUseCase = DiscoverMovieProviderUseCase()

    private val discoverSerieProviderUseCase = DiscoverSerieProviderUseCase()

    private val searchUseCase = SearchUseCase()

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

    private var _movieAPIList = MutableStateFlow<List<MovieOrSerieState>>(emptyList())

    private var _movieList = MutableStateFlow<List<MovieOrSerieState>>(emptyList())
    var movieList : StateFlow<List<MovieOrSerieState>> = _movieList.asStateFlow()

    private var _serieAPIList = MutableStateFlow<List<MovieOrSerieState>>(emptyList())

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

    private var _similarMoviesAndSeries = MutableStateFlow<List<MovieOrSerieState>>(emptyList())
    var similarMoviesAndSeries : StateFlow<List<MovieOrSerieState>> = _similarMoviesAndSeries

    private val _selectedMovieOrSerie = MutableStateFlow(MovieOrSerieState())
    var selectedMovieOrSerie : StateFlow<MovieOrSerieState> = _selectedMovieOrSerie

    private val _lastSelectedMoviesOrSeries = MutableStateFlow<List<MovieOrSerieState>>(emptyList())

    private val _cinemaList = MutableStateFlow<List<MovieOrSerieState>>(emptyList())
    val cinemaList : StateFlow<List<MovieOrSerieState>> = _cinemaList

    private val _showTrailer = MutableStateFlow(false)
    val showTrailer : StateFlow<Boolean> = _showTrailer

    private var _actualGenre = MutableStateFlow("")

    private var _selectedGenreText = MutableStateFlow("")
    var selectedGenreText : StateFlow<String> = _selectedGenreText

    private var _searchByGenrePosition = MutableStateFlow(0)
    var searchByGenrePosition : StateFlow<Int> = _searchByGenrePosition

    var genresToShow = listOf("Crime","Comedy","Animation","Action","Adventure","Fantasy","Horror","Romance","Mystery","Western")

    private var _providerList = MutableStateFlow<List<MovieOrSerieProviderState>>(emptyList())
    var providerList : StateFlow<List<MovieOrSerieProviderState>> = _providerList

    private var _platformLink = MutableStateFlow("")

    var userSearch by mutableStateOf("")
        private set

    private val _searchList = MutableStateFlow<List<MovieOrSerieState>>(emptyList())
    val searchList : StateFlow<List<MovieOrSerieState>> = _searchList

    var loadingDB by mutableStateOf(false)
        private set

    init {
        //Hacemos una primera búsqueda de películas y series en la base de datos al iniciar la aplicación
        fetchMoviesFromDB()
        fetchSeriesFromDB()
        //Buscamos los distintos géneros que hay de películas y series
        movieGenres()
        serieGenres()
        //Buscamos y guardamos la información del usuario que ha iniciado sesión
        findUserInDB()
        //Buscamos las películas que se encuentran actualmente en el cine en la API
        getCinemaMovies()
    }

    /**
     * Muestra en la pantalla que se está actualizando la base de datos
     */
    fun showLoading(){
        loadingDB = true
    }

    /**
     * Realiza la búsqueda del usuario en la API
     */
    fun search(){
        viewModelScope.launch(Dispatchers.IO) {
            _searchList.value = searchUseCase.invoke(userSearch).results
        }
    }

    /**
     * Muestra una nueva película o serie en la pantalla de buscar por género
     */
    fun newMovieOrSerie(){
        _propertyButton.value = Property1.Default
        if (_searchByGenrePosition.value == _moviesAndSeriesByGenreList.value.size-1){
            _searchByGenrePosition.value=_moviesAndSeriesByGenreList.value.size-1
        }else{
            _searchByGenrePosition.value++
        }
    }

    /**
     * Muestra la película o serie anterior en la pantalla de buscar por género
     */
    fun lastMovieOrSerie(){
        _propertyButton.value = Property1.Default
        if (_searchByGenrePosition.value == 0){
            _searchByGenrePosition.value=0
        }else{
            _searchByGenrePosition.value--
            findMovieOrSerieInList(_moviesAndSeriesByGenreList.value[_searchByGenrePosition.value].title)
        }
    }

    /**
     * Guarda la película o serie que se ha seleccionado en la variable del ViewModel
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
                _moviesAndSeriesByGenreList.value = moviesAndSeries.distinct().shuffled()
            }
    }

    /**
     * Buscamos una lista de películas en la API
     *
     * @param page página de la API que hay que buscar
     */
    private fun getAllMovies(page:Int){
        viewModelScope.launch(Dispatchers.IO) {
            _movieAPIList.value = discoverMoviesUseCase.invoke(page).results
        }
    }

    /**
     * Buscamos una lista de series en la API
     *
     * @param page página de la API que hay que buscar
     */
    private fun getAllSeries(page:Int){
        viewModelScope.launch(Dispatchers.IO) {
            _serieAPIList.value = discoverSeriesUseCase.invoke(page).results
        }
    }

    /**
     * Busca las plataformas en las que se encuentra una película o serie
     *
     * @param movieOrSerie película o serie de la que queremos buscar las plataformas
     */
    fun getWatchProvider(movieOrSerie: MovieOrSerieState){
        if (movieOrSerie.type == "Serie"){
            viewModelScope.launch {
                _providerList.value = discoverSerieProviderUseCase.invoke(movieOrSerie.idAPI).providerList
            }
        }else{
            viewModelScope.launch {
                _providerList.value = discoverMovieProviderUseCase.invoke(movieOrSerie.idAPI).providerList
            }
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
        reset()
    }

    /**
     * Realiza una búsqueda amplia de películas y series en la API para introducirlas en la base de datos de Firebase
     */
    private fun loadFromAPI() {
        viewModelScope.launch(Dispatchers.IO) {
            loadingDB = true
            val temporalList = mutableListOf<List<MovieOrSerieState>>()
            for (z in 1 until 20) {
                getAllSeries(z)
                getAllMovies(z)
                Thread.sleep(500)
                temporalList.add(_serieAPIList.value)
                temporalList.add(_movieAPIList.value)
            }
            _dbList.value = temporalList
            saveInDB()
        }
    }

    /**
     * Guarda todas las películas y series obtenidas en la base de datos
     */
    private fun saveInDB() {
        viewModelScope.launch(Dispatchers.IO) {
            _dbList.value?.forEach { list ->
                list.forEach { movieOrSerie ->
                    saveMovieOrSerie(movieOrSerie)
                }
            }
            loadingDB = false
        }
    }

    /**
     * Borra todas las películas y series que están guardadas en la base de datos para actualizarlas
     */
    fun restartDB() {
        viewModelScope.launch(Dispatchers.IO) {
            loadingDB = true
            _dbList.value?.forEach { list ->
                list.forEach { movieOrSerie ->
                    try {
                        firestore.collection("MoviesAndSeries").document(movieOrSerie.idDoc).delete().await()
                    } catch (e: Exception) {
                        print(e.localizedMessage)
                    }
                }
            }
            loadFromAPI()
        }
    }


    /**
     * Buscamos los géneros que hay entre películas y series
     * para que no se repitan si alguno coincide
     * y los guardamos en la variable _genres
     *
     * @param genre género seleccionado para filtrar
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
                _selectedGenreText.value = value
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
     * Guarda la película o serie que se ha seleccionado en la variable del ViewModel
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

    /**
     * Buscamos películas y series similares a partir de otra
     *
     * @param movieOrSerie película o serie de la que queremos buscar similares
     */
    fun findSimilarMoviesOrSeries(movieOrSerie: MovieOrSerieState){
        if (movieOrSerie.type == "Movie"){
            viewModelScope.launch {
                _similarMoviesAndSeries.value = getSimilarMoviesUseCase.invoke(movieOrSerie.idAPI).results.filter { it.poster != null && !it.overview.isNullOrEmpty() }
            }
        }else{
            viewModelScope.launch {
                _similarMoviesAndSeries.value = getSimilarSeriesUseCase.invoke(movieOrSerie.idAPI).results.filter { it.poster != null && !it.overview.isNullOrEmpty() }
            }
        }
    }

    /**
     * Comprueba si el nombre de la película ya se encuentra en la base de datos
     * para mostrar el botón de guardado correspondiente
     *
     * @param title título de la película o serie que queremos comprobar
     */
    fun findMovieOrSerieInList(title: String){
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
     * @param movieOrSerie título de la película o serie de la que vamos a buscar los comentarios
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
                _movieList.value = movies.distinct().shuffled()
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
                _serieList.value = series.distinct().shuffled()
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
     * Muestra una nueva película en la pantalla de películas
     */
    fun newMovie(){
        _propertyButton.value = Property1.Default
        if (_moviePosition.value == _movieList.value.size-1){
            _moviePosition.value=0
        }else{
            _moviePosition.value++
            findMovieOrSerieInList(_movieList.value[_moviePosition.value].title)
        }
    }

    /**
     * Muestra la película anterior en la pantalla de películas
     */
    fun lastMovie(){
        _propertyButton.value = Property1.Default
        if (_moviePosition.value == 0){
            _moviePosition.value=0
        }else if(_moviePosition.value <= 0) {
            _moviePosition.value = _movieList.value.size - 1
        }else{
            _moviePosition.value--
            findMovieOrSerieInList(_movieList.value[_moviePosition.value].title)
        }
    }

    /**
     * Muestra una nueva serie en la pantalla de series
     */
    fun newSerie(){
        _propertyButton.value = Property1.Default
        if (_seriePosition.value == _serieList.value.size-1){
            _seriePosition.value=0
        }else{
            _seriePosition.value++
            findMovieOrSerieInList(_serieList.value[_seriePosition.value].title)
        }
    }

    /**
     * Muestra la serie anterior en la pantalla de series
     */
    fun lastSerie(){
        _propertyButton.value = Property1.Default
        if (_seriePosition.value == 0){
            _seriePosition.value=0
        } else if (_seriePosition.value <= 0) {
            _seriePosition.value = _movieList.value.size - 1
        }else{
            _seriePosition.value--
            findMovieOrSerieInList(_serieList.value[_seriePosition.value].title)
        }
    }

    /**
     * Cambia la propiedad del botón de guardado dependiendo de cuando se pulsa
     */
    private fun guardarPeliculaOSerie(movieOrSerie : MovieOrSerieState){
        if (_propertyButton.value == Property1.Default){
            _actualMovieOrSerieState.value = movieOrSerie
            _propertyButton.value = Property1.Guardado
        }else{
            _propertyButton.value = Property1.Default
        }
    }


    /**
     * Guarda la película o serie que se le indica en la base de datos de favoritos
     *
     * @param searchMovieState película o serie que queremos añadir a favoritos
     */
    fun saveMovieOrSerieInFavorites(searchMovieState: MovieOrSerieState) {
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
                    "emailUser" to email.toString(),
                    "type" to searchMovieState.type
                )
                firestore.collection("Favoritos")
                    .add(newMovieOrSerie)
                    .addOnSuccessListener {
                        guardarPeliculaOSerie(searchMovieState)
                        fetchFavoritesFromDB()
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
     * Guarda la película o serie que se le indica en la base de datos de películas y series
     *
     * @param movieOrSerieState película o serie que queremos añadir a favoritos
     */
    private fun saveMovieOrSerie(movieOrSerieState: MovieOrSerieState) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newMovieOrSerie = hashMapOf(
                    "idAPI" to movieOrSerieState.idAPI,
                    "title" to movieOrSerieState.title,
                    "overview" to movieOrSerieState.overview,
                    "poster" to movieOrSerieState.poster,
                    "date" to movieOrSerieState.date,
                    "votes" to movieOrSerieState.votes,
                    "genres" to movieOrSerieState.genres,
                    "type" to movieOrSerieState.type
                )
                firestore.collection("MoviesAndSeries")
                    .add(newMovieOrSerie)
                    .addOnFailureListener {
                        throw Exception()
                    }
            } catch (e: Exception){
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
                        guardarPeliculaOSerie(_actualMovieOrSerieState.value)
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
     *
     * @param title título de la película o serie a buscar
     */
    fun formatTitle(title: String){
        val result = "official trailer "
        getTrailer(result+title)
    }

    /**
     * Busca el trailer de la película o serie en la API
     *
     * @param title título de la película o serie a buscar
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
     * Reinicia las variables necesarias cuando un usuario cierra sesión
     */
    fun reset(){
        _searchByGenrePosition.value = 0
        _selectedGenreText.value = ""
        _actualGenre.value = ""
        _selectedMovieOrSerie.value = MovieOrSerieState()
        _moviesAndSeriesByGenreList.value = emptyList()
        _moviePosition.value = 0
        _seriePosition.value = 0
        _userName.value = ""
        _propertyButton.value = Property1.Default
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

    /**
     * Reinicia el valor de la búsqueda
     */
    fun resetSearch(){
        userSearch = ""
    }

    /**
     * Determina el enlace al que debe navegar dependiendo de la plataforma en la que pulse
     *
     * @param provier plataforma sobre la que se ha pulsado
     * @param context contexto en el que se navega al enlace
     */
    fun clickWatchProvider(provier: MovieOrSerieProviderState, context: Context){
        when(provier.provider_name){
            "Disney Plus" -> _platformLink.value = "https://www.disneyplus.com/es-es"
            "Netflix" -> _platformLink.value = "https://www.netflix.com/es/"
            "Amazon Prime Video" -> _platformLink.value = "https://www.primevideo.com/-/es/offers/nonprimehomepage/ref=atv_hom_offers_c_9zZ8D2_hom?language=es"
            "fuboTV" -> _platformLink.value = "https://www.fubo.tv/stream/latino-es/"
            "Apple TV" -> _platformLink.value = "https://tv.apple.com/es"
            "Apple TV Plus" -> _platformLink.value = "https://www.apple.com/es/apple-tv-plus/"
            "Filmin" -> _platformLink.value = "https://www.filmin.es/?utm_medium=Email&utm_source=Borrador%20NdP%202"
            "Filmin Plus" -> _platformLink.value = "https://www.filmin.es/?utm_medium=Email&utm_source=Borrador%20NdP%202"
            "SkyShowtime" -> _platformLink.value = "https://www.skyshowtime.com/es?lid=wycodr8y54th&market=es&LoB=sst&Channel=sea&Platform=gad&Mobj=acq&campaign_name=23q1aomulti&Campaign_ID=71700000105085578&Placement_ID=na&Creative_ID=na&dispatch_id=na&datena&gad_source=1&gclid=Cj0KCQjwjLGyBhCYARIsAPqTz1_Qg3_TWDhIxpxiCf6PwsWvuwCKpHcT7ysCSZr7RTV1lwCeQPLMm1YaAo62EALw_wcB&gclsrc=aw.ds"
            "Movistar Plus" -> _platformLink.value = "https://ver.movistarplus.es/?id_perfil=OTT&suscripcion=UT-UTE0H,UT-UTXC0,UT-UTXIG,UT-UTXIH,UT-UTXIJ,UT-UTXIW&promo=PRFICC&ui=FICCIO&demarcation=15"
            "HBO Max" -> _platformLink.value = "https://www.max.com/es/es"
            "Rakuten TV" -> _platformLink.value = "https://www.rakuten.tv/es"
            "Atres Player" -> _platformLink.value = "https://www.atresplayer.com/"
            "Google Play Movies" -> _platformLink.value = "https://play.google.com/store/movies?hl=es&gl=US&pli=1"
            "Microsoft Store" -> _platformLink.value = "https://www.microsoft.com/es-ad/store/b/home"
            "MUBI" -> _platformLink.value = "https://mubi.com/es/es"
            "GuideDoc" -> _platformLink.value = "https://guidedoc.tv/"
            "YouTube Premium" -> _platformLink.value = "https://www.youtube.com/premium?ybp=Sg0IBhIJdW5saW1pdGVk4AEB"
            "FlixOlé" -> _platformLink.value = "https://flixole.com/"
            "Curiosity Stream" -> _platformLink.value = "https://curiositystream.com/es?utm_source=google&utm_medium=cpc&utm_campaign={campaign}&utm_content=659044019910&utm_term=curiosity%20stream&utm_campaign={campaign}&utm_medium=cpc&utm_source=google&utm_content=659044019910&utm_term=curiosity%20stream&gad_source=1&gclid=Cj0KCQjwjLGyBhCYARIsAPqTz1_wXmeyUhtwNMibIWobJEHPz0jIzZmsu8hVrHzv6sWKylsHR-9WRBsaAjlMEALw_wcB"
            "Mitele" -> _platformLink.value = "https://www.mitele.es/"
            "DOCSVILLE" -> _platformLink.value = "https://www.docsville.com/"
            "Spamflix" -> _platformLink.value = "https://spamflix.com/home.do"
            "rtve" -> _platformLink.value = "https://www.rtve.es/"
            "Plex" -> _platformLink.value = "https://www.plex.tv/es/"
            "WOW Presents Plus" -> _platformLink.value = "https://es.wowpresentsplus.com/?utm_source=google.com&utm_medium=organic"
            "Magellan TV" -> _platformLink.value = "https://www.magellantv.com/featured"
            "BroadwayHD" -> _platformLink.value = "https://www.broadwayhd.com/"
            "Filmzie" -> _platformLink.value = "https://filmzie.com/home"
            "Dekkoo" -> _platformLink.value = "https://www.dekkoo.com/"
            "True Story" -> _platformLink.value = "https://www.truestory.film/"
            "DocAlliance Films" -> _platformLink.value = "https://www.docalliance.org/"
            "Hoichoi" -> _platformLink.value = "https://www.hoichoi.tv/"
            "Amazon Video" -> _platformLink.value = "https://www.primevideo.com/offers/nonprimehomepage/ref=dvm_pds_amz_ES_lb_s_g_mkw_sygw97QF2-dc_pcrid_637462221524?gclid=Cj0KCQjwjLGyBhCYARIsAPqTz193XrZP2Q43fNdH7ejpVZhU2IpB3PIIkDgHj8UFgweWQLXsXVkmaN8aAndnEALw_wcB&mrntrk=slid__pgrid_84338514223_pgeo_1005444_x__adext__ptid_kwd-297838409925"
            "Pluto TV" -> _platformLink.value = "https://pluto.tv/es/live-tv/65786a1ccbd0d60008f7e2a9?lang=en"
            "Eventive" -> _platformLink.value = "https://eventive.org/"
            "MUBI Amazon Channel" -> _platformLink.value = "https://www.primevideo.com/offers/?benefitId=mubies&ref_=DVM_PDS_CHN_ES_mi_C_mkw_s0sPBvTrG-dc_pcrid_658404667329&mrntrk=slid__pgrid_146344738942_pgeo_1005444_x__adext__ptid_kwd-308010117976&gclid=Cj0KCQjwjLGyBhCYARIsAPqTz19_0g8lj50isY_Hwj5oyAzjJY5O9K4jldipKtoYm5xHUaWcmgIeZR0aAmCAEALw_wcB"
            "OUTtv Amazon Channel" -> _platformLink.value = "https://www.primevideo.com/offers/?benefitId=outtves&ref_=DVM_PDS_CHN_ES_mi_C_mkw_shw2sIq6l-dc_pcrid_658404667335&mrntrk=slid__pgrid_146344739022_pgeo_1005444_x__adext__ptid_kwd-758826826305&gclid=Cj0KCQjwjLGyBhCYARIsAPqTz1_z7fkp40kgXsA8LxMgbnyz_EMg0chT6aZuZiDuDfthVB8WY4PFTRkaAmQvEALw_wcB"
            "AcornTV Amazon Channel" -> _platformLink.value = "https://www.primevideo.com/offers/ref=atv_nb_lcl_zh_CN?ref=dvm_pds_chn_ca_dc_c_g%7Cm_8A0RKmYrc_c353290793170&benefitId=acorntvca&ie=UTF8&language=es_ES&gclid=EAIaIQobChMIwPu5t4qs3AIVAQAAAB0BAAAAEAAYACAAEgJVzfD_BwE"
            "MGM Amazon Channel" -> _platformLink.value = "https://www.primevideo.com/offers/ref=atv_nb_lcl_es_ES?benefitId=mgmfr&ref=DVM_PDS_PDS_FR_SB_C__469782380371__m&language=es_ES&ie=UTF8"
            "FlixOlé Amazon Channel" -> _platformLink.value = "https://www.primevideo.com/offers/?benefitId=flixolees&ref_=DVM_PDS_CHN_ES_mi_C_mkw_sbcwuo5ih-dc_pcrid_638885515625&mrntrk=slid__pgrid_143367867999_pgeo_1005444_x__adext__ptid_kwd-1927779810393&gclid=Cj0KCQjwjLGyBhCYARIsAPqTz1_coge5grsmh5RtMlZEIB_vX_wodpPZQK-yJVGr1WUhh9i-V6NnQlIaAmNkEALw_wcB"
            "TVCortos Amazon Channel" -> _platformLink.value = "https://shorts.tv/es/tv"
            "Cultpix" -> _platformLink.value = "https://www.cultpix.com/movies/Films%20Free%20to%20View%20for%20All"
            "FilmBox+" -> _platformLink.value = "https://www.filmbox.com/int/home?locale=en-US"
            "Acontra Plus" -> _platformLink.value = "https://acontraplus.com/"
            "Planet Horror Amazon Channel" -> _platformLink.value = "https://www.primevideo.com/offers/?benefitId=planethorrores&ref_=DVM_PDS_CHN_ES_mi_C_mkw_s0ihOivA8-dc_pcrid_658404667341&mrntrk=slid__pgrid_146344739222_pgeo_1005444_x__adext__ptid_kwd-1927779811873&gclid=Cj0KCQjwjLGyBhCYARIsAPqTz191zq8b0nzmuaW9eIYLmmvEzZylFMueWTET3JMtrk882VnbSgQB-jsaArcXEALw_wcB"
            "Dizi Amazon Channel" -> _platformLink.value = "https://www.primevideo.com/offers/?benefitId=dizies&ref_=DVM_PDS_CHN_ES_mi_C_mkw_smTwCDK5e-dc_pcrid_658404667182&mrntrk=slid__pgrid_146344738222_pgeo_1005444_x__adext__ptid_kwd-624685590644&gclid=Cj0KCQjwjLGyBhCYARIsAPqTz194v8TARPOGbevgnWGbVmG9EN-RpeJ3126lt6zcFWnRu-Icb6DK2ZMaAh1TEALw_wcB"
            "Acontra Plus Amazon Channel" -> _platformLink.value = "https://www.primevideo.com/offers/?benefitId=acontraes&ref_=DVM_PDS_CHN_ES_mi_C_mkw_soRKmT4Ka-dc_pcrid_638885515622&mrntrk=slid__pgrid_143367867839_pgeo_1005444_x__adext__ptid_kwd-1927779808513&gclid=Cj0KCQjwjLGyBhCYARIsAPqTz1_xUpMnTMRQiT3XSdWmzQP6-_5YkWlZ9yL9MwLo5rRIUfiMehTz9I4aAnPdEALw_wcB"
            "Historia y Actualidad Amazon Channel" -> _platformLink.value = "https://www.primevideo.com/offers/?benefitId=historiaes&ref_=DVM_PDS_CHN_ES_mi_C_mkw_sTajwB6fy-dc_pcrid_658404667194&mrntrk=slid__pgrid_146344738502_pgeo_1005444_x__adext__ptid_kwd-1927779810633&gclid=Cj0KCQjwjLGyBhCYARIsAPqTz19KyKjmXkq5rD_39On1K2CuttIZ0vY0HluXNo9iR3Q5T-MCwHa-b8saAouaEALw_wcB"
            "Takflix" -> _platformLink.value = "https://takflix.com/uk"
            "Sun Nxt" -> _platformLink.value = "https://www.sunnxt.com/"
            "Classix" -> _platformLink.value = "https://www.classixapp.com/"
            "Netflix basic with Ads" -> _platformLink.value = "https://www.netflix.com/es/"
            "Tivify" -> _platformLink.value = "https://www.tivify.es/es/"
            "Runtime" -> _platformLink.value = "https://www.runtime.tv/"
            "Crunchyroll" -> _platformLink.value = "https://www.crunchyroll.com/es/"
            "AMC+ Amazon Channel" -> _platformLink.value = "https://www.amazon.com/-/es/gp/video/offers?benefitId=amcplus"
            "Love Nature Amazon Channel" -> _platformLink.value = "https://www.amazon.com/gp/video/storefront/ref=dvah?benefitId=lovenatureca&node=2858778011"
            "Shahid VIP" -> _platformLink.value = "https://shahid.mbc.net/en"
            "Acorn TV Apple TV" -> _platformLink.value = "https://tv.apple.com/es/room/acorn-tv/edt.item.62914943-2c47-472c-ae22-934f48436969?ctx_brand=tvs.sbd.1000383"
            "AMC Plus Apple TV Channel " -> _platformLink.value = "https://tv.apple.com/us/room/amc/edt.item.5f47e242-dc79-470a-b936-f61eaae0a028"
            "Kocowa" -> _platformLink.value = "https://www.kocowa.com/es_es/guide"
            "Chili" -> _platformLink.value = "https://es.chili.com/"
            "Hayu" -> _platformLink.value = "https://get.hayu.com/welcome?geo=ES&gad_source=1&gclid=Cj0KCQjw0ruyBhDuARIsANSZ3wo92TAQoNttoKPZjKg6aIzCnql-bvPkLz6hZVa8_Ni8xiy76v0TmfsaArCgEALw_wcB&gclsrc=aw.ds"
            "Discovery+" -> _platformLink.value = "https://www.discoveryplus.com/es/"
            "Hayu Amazon Channel" -> _platformLink.value = "https://www.primevideo.com/offers/?benefitId=hayues&ref_=DVM_PDS_CHN_ES_mi_C_mkw_sO2tY5O86-dc_pcrid_658404667191&mrntrk=slid__pgrid_146344738462_pgeo_1005444_x__adext__ptid_kwd-307930840022&gclid=Cj0KCQjw0ruyBhDuARIsANSZ3woaKK-2TXL76549AM8l-AJJWn0dDoY9i0UF6VuxX2Bla6G1VWg0IIoaAg97EALw_wcB"
            "Noggin Amazon Channel" -> _platformLink.value = "https://www.primevideo.com/offers/ref=atv_nb_lcl_es_ES?benefitId=nogginfr&language=es_ES&ie=UTF8"
            "Pash Amazon Channel" -> _platformLink.value = "https://www.primevideo.com/offers/?benefitId=pashes&ref_=DVM_PDS_CHN_ES_mi_C_mkw_sO3G5Ez3N-dc_pcrid_658404667338&mrntrk=slid__pgrid_146344739182_pgeo_1005444_x__adext__ptid_kwd-570999103429&gclid=Cj0KCQjw0ruyBhDuARIsANSZ3wof_ok2Zrzq-coSpgavlaKPh3S-qb9Imk31tfDq6vCHB_0t-r_A4wMaAkfjEALw_wcB"
            "Hopster Amazon Channel" -> _platformLink.value = "https://www.primevideo.com/offers/?benefitId=hopsteres&ref_=DVM_PDS_CHN_ES_mi_C_mkw_sKS8cV5Mi-dc_pcrid_658404667197&mrntrk=slid__pgrid_146344738542_pgeo_1005444_x__adext__ptid_kwd-307975369100&gclid=Cj0KCQjw0ruyBhDuARIsANSZ3woIInp1rn0T8gQTOfBqrTx_rq_q0fQ11ifal9hIguNm1Blxi2oeG2QaArqMEALw_wcB"
            "Noggin Apple TV Channel" -> _platformLink.value = "https://tv.apple.com/us/show/noggin-knows/umc.cmc.13fruiqc3rpcotwyipmhq0uve"

        }
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(_platformLink.value))
        context.startActivity(intent)
    }
}