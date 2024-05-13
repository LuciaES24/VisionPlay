package com.lespsan543.visionplay.app.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lespsan543.visionplay.app.domain.GetMovieGenresUseCase
import com.lespsan543.visionplay.app.domain.GetSerieGenresUseCase
import com.lespsan543.visionplay.app.domain.GetTrailerUseCase
import com.lespsan543.visionplay.app.ui.states.MovieOrSerieState
import com.lespsan543.visionplay.guardar.Property1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class SearchGenresViewModel : ViewModel(){
    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore

    private val getMovieGenresUseCase = GetMovieGenresUseCase()

    private val getSerieGenresUseCase = GetSerieGenresUseCase()

    private val getTrailerUseCase = GetTrailerUseCase()

    private var _moviesInDB = MutableStateFlow<List<MovieOrSerieState>>(emptyList())

    var genresToShow = listOf("Crime","Comedy","Animation","Action","Adventure", "Fantasy","Horror","Romance","Mystery","Western")

    private var _movieGenres = MutableStateFlow<Map<String,String>>(emptyMap())

    private var _serieGenres = MutableStateFlow<Map<String,String>>(emptyMap())

    private var _genres = MutableStateFlow<Map<String,String>>(emptyMap())

    private var _moviesAndSeriesList = MutableStateFlow<List<MovieOrSerieState>>(emptyList())
    val moviesAndSeriesList : StateFlow<List<MovieOrSerieState>> = _moviesAndSeriesList

    private var _actualGenre = MutableStateFlow("")

    private var _favoriteList = MutableStateFlow<List<MovieOrSerieState>>(emptyList())

    private var _propertyButton = MutableStateFlow(Property1.Default)
    var propertyButton : StateFlow<Property1> = _propertyButton

    private var _position = MutableStateFlow(0)
    var position : StateFlow<Int> = _position

    private var _actualSearchMovieState = MutableStateFlow(MovieOrSerieState())

    private var _showGenres = MutableStateFlow("")
    var showGenres : StateFlow<String> = _showGenres

    private var _trailerId = MutableStateFlow("")
    var trailerId : StateFlow<String> = _trailerId

    init {
        movieGenres()
        serieGenres()
    }

    /**
     * Reinicia el número de página de la API y la posición al cambiar de género
     */
    fun reset(){
        _position.value = 0
    }

    /**
     * Busca todas las películas y series que ya están añadidas a favoritos
     * en la base de datos
     */
    fun fetchFavotitesFromDB() {
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
     * Buscamos los distintos géneros de las películas
     */
    private fun movieGenres(){
        viewModelScope.launch(Dispatchers.IO) {
            _movieGenres.value = getMovieGenresUseCase.invoke()
        }
    }

    /**
     * Comprueba si el nombre de la película o serie ya se encuentra en la base de datos
     * para mostrar el botón de guardado correspondiente
     *
     * @param title título de la película o serie que queremos comprobar
     */
    fun findMovieOrSerieInList(title: String){
        for (movie in _moviesInDB.value) {
            if (title == movie.title){
                _propertyButton.value = Property1.Guardado
                _actualSearchMovieState.value = movie
            }
        }
    }

    /**
     * Muestra una nueva película o serie
     */
    fun newMovieOrSerie(){
        _propertyButton.value = Property1.Default
        if (_position.value == _favoriteList.value.size-1){
            _position.value=0
        }else{
            _position.value++
        }
    }

    /**
     * Busca todas las películas y series de la base de datos a partir del género seleccionado
     */
    private fun fetchMoviesFromDB() {
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
                _moviesAndSeriesList.value = moviesAndSeries
            }
    }

    /**
     * Muestra la película o serie anterior
     */
    fun lastMovieOrSerie(){
        _propertyButton.value = Property1.Default
        if (_position.value == 0){
            _position.value=0
        }else{
            _position.value--
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
     * Obtiene el nombre de los géneros de la película o serie que se indica
     *
     * @param movieOrSerie película o serie de la que vamos a obtener los géneros
     */
    fun getGenresToShow(movieOrSerie: MovieOrSerieState){
        var genres = ""
        for (i in movieOrSerie.genres){
            val genre = _genres.value.get(i)
            genres+=genre+"\n"
        }
        _showGenres.value = genres
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
        fetchMoviesFromDB()
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