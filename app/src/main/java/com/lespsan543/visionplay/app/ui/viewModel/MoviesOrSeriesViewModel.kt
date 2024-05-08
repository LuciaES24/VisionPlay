package com.lespsan543.visionplay.app.ui.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.lespsan543.visionplay.app.domain.GetMovieGenresUseCase
import com.lespsan543.visionplay.app.domain.GetSerieGenresUseCase
import com.lespsan543.visionplay.app.domain.GetTrailerUseCase
import com.lespsan543.visionplay.app.ui.states.MovieOrSerieState
import com.lespsan543.visionplay.guardar.Property1
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
 * @property _moviesInDB flujo de datos de las películas que se han recogido de la base de datos
 * @property _actualSearchMovieState flujo de datos de la película actual que se está mostrando con los datos de la base de datos
 */
class MoviesOrSeriesViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore

    private val discoverMoviesUseCase = DiscoverMoviesUseCase()

    private val discoverSeriesUseCase = DiscoverSeriesUseCase()

    private val getMovieGenresUseCase = GetMovieGenresUseCase()

    private val getSerieGenresUseCase = GetSerieGenresUseCase()

    private val getTrailerUseCase = GetTrailerUseCase()

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

    private var _moviesInDB = MutableStateFlow<List<MovieOrSerieState>>(emptyList())

    private var _actualSearchMovieState = MutableStateFlow(MovieOrSerieState())

    private var _movieGenres = MutableStateFlow<Map<String,String>>(emptyMap())

    private var _serieGenres = MutableStateFlow<Map<String,String>>(emptyMap())

    private var _showGenres = MutableStateFlow("")
    var showGenres : StateFlow<String> = _showGenres

    private var _trailerId = MutableStateFlow("")
    var trailerId : StateFlow<String> = _trailerId

    private var _commentsList = MutableStateFlow<List<CommentModel>>(emptyList())
    var commentsList : StateFlow<List<CommentModel>> = _commentsList

    private var _userName = MutableStateFlow("")

    var commentText = mutableStateOf("")

    init {
        //Hacemos una primera búsqueda de películas y series al iniciar la aplicación
        fetchMoviesFromDB()
        fetchSeriesFromDB()
        movieGenres()
        serieGenres()
        findUserInDB()
    }

    /**
     * Obtiene el nombre de los géneros de la película que se indica
     *
     * @param movie película de la que vamos a obtener los géneros
     */
    fun getMovieGenresToShow(movie: MovieOrSerieState){
        var genres = ""
        for (i in movie.genres){
            val genre = _movieGenres.value.get(i)
            genres+=genre+"\n"
        }
        _showGenres.value = genres
    }

    /**
     * Obtiene el nombre de los géneros de la serie que se indica
     *
     * @param serie serie de la que vamos a obtener los géneros
     */
    fun getSerieGenresToShow(serie: MovieOrSerieState){
        var genres = ""
        for (i in serie.genres){
            val genre = _serieGenres.value.get(i)
            genres+=genre+"\n"
        }
        _showGenres.value = genres
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
     * Comprueba si el nombre de la película ya se encuentra en la base de datos
     * para mostrar el botón de guardado correspondiente
     *
     * @param title título de la película o serie que queremos comprobar
     */
    fun findMovieInList(title: String){
        for (movie in _moviesInDB.value) {
            if (title == movie.title){
                _propertyButton.value = Property1.Guardado
                _actualSearchMovieState.value = movie
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
        firestore.collection("Users")
            .whereEqualTo("id",  auth.currentUser?.uid)
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

    fun writeComment(new: String){
        commentText.value = new
    }

    fun newComment(){
        commentText.value = "";
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
                _moviesInDB.value = movies
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
                firestore.collection("Favoritos").document(_actualSearchMovieState.value.idDoc)
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

    fun formatTitle(title: String){
        val result = "official trailer "
        getTrailer(result+title)
    }

    private fun getTrailer(title: String){
        viewModelScope.launch(Dispatchers.IO) {
            _trailerId.value = getTrailerUseCase.invoke(title)
        }
    }

    fun resetTrailer(){
        _trailerId.value = ""
    }
}