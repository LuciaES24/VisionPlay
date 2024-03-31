package com.lespsan543.visionplay.app.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lespsan543.visionplay.app.domain.DiscoverMoviesUseCase
import com.lespsan543.visionplay.app.domain.DiscoverSeriesUseCase
import com.lespsan543.visionplay.app.domain.GetMovieGenresUseCase
import com.lespsan543.visionplay.app.domain.GetSerieGenresUseCase
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

    private val discoverMoviesUseCase = DiscoverMoviesUseCase()

    private val discoverSeriesUseCase = DiscoverSeriesUseCase()

    private var _moviesInDB = MutableStateFlow<List<MovieOrSerieState>>(emptyList())

    var genresToShow = listOf("Crime","Comedy","Animation","Action","Adventure", "Fantasy","Horror","Romance","Mystery","Western")

    private var _movieGenres = MutableStateFlow<Map<String,String>>(emptyMap())

    private var _serieGenres = MutableStateFlow<Map<String,String>>(emptyMap())

    private var _genres = MutableStateFlow<Map<String,String>>(emptyMap())

    private var _movieList = MutableStateFlow<List<MovieOrSerieState>>(emptyList())

    private var _serieList = MutableStateFlow<List<MovieOrSerieState>>(emptyList())

    private var _apiMoviePage = MutableStateFlow(1)

    private var _apiSeriePage = MutableStateFlow(1)

    private var _actualGenre = MutableStateFlow("")

    private var _moviesAndSeriesList = MutableStateFlow<List<MovieOrSerieState>>(emptyList())
    val moviesAndSeriesList : StateFlow<List<MovieOrSerieState>> = _moviesAndSeriesList

    private var _propertyButton = MutableStateFlow(Property1.Default)
    var propertyButton : StateFlow<Property1> = _propertyButton

    private var _position = MutableStateFlow(0)
    var position : StateFlow<Int> = _position

    private var _actualSearchMovieState = MutableStateFlow(MovieOrSerieState())

    private var _showGenres = MutableStateFlow("")
    var showGenres : StateFlow<String> = _showGenres

    init {
        getAllMovies()
        getAllSeries()
        movieGenres()
        serieGenres()
    }

    /**
     * Reinicia el número de página de la API y la posición al cambiar de género
     */
    fun reset(){
        _apiMoviePage.value = 1
        _apiSeriePage.value = 1
        _position.value = 0
    }

    /**
     * Busca todas las películas y series que ya están añadidas a favoritos
     * en la base de datos
     */
    fun fetchMoviesInDB() {
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
        if (_position.value == _moviesAndSeriesList.value.size-1){
            _apiMoviePage.value++
            _apiSeriePage.value++
            _position.value=0
            getAllMovies()
            getAllSeries()
            diferentGenres(_actualGenre.value)
        }else{
            _position.value++
        }
    }

    /**
     * Muestra la película o serie anterior
     */
    fun lastMovieOrSerie(){
        _propertyButton.value = Property1.Default
        if (_apiMoviePage.value==1 && _position.value == 0 || _apiSeriePage.value == 1 && _position.value == 0){
            _apiMoviePage.value=1
            _apiSeriePage.value=1
            _position.value=0
        }else if(_position.value <= 0) {
            _apiMoviePage.value--
            _apiSeriePage.value--
            _position.value = _moviesAndSeriesList.value.size - 1
            getAllMovies()
            getAllSeries()
            diferentGenres(_actualGenre.value)
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
     * Buscamos los géneros que hay entre películas y series
     * para que no se repitan si alguno coincide
     * y los guardamos en la variable _genres
     *
     * @param genre género a buscar
     */
    fun diferentGenres(genre : String){
        val temporalGenresMap = mutableMapOf<String, String>()
        for ((key, value) in _movieGenres.value){
            temporalGenresMap[key] = value
        }
        for ((key, value) in _serieGenres.value){
            if (key !in _genres.value.keys){
                temporalGenresMap[key] = value
            }
        }
        _genres.value = temporalGenresMap
        getActualGenre(genre)
    }

    /**
     * Buscamos una lista de películas en la API
     */
    private fun getAllMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            _movieList.value = discoverMoviesUseCase.invoke(_apiMoviePage.value).results
        }
    }

    /**
     * Buscamos una lista de series en la API
     */
    private fun getAllSeries(){
        viewModelScope.launch(Dispatchers.IO) {
            _serieList.value = discoverSeriesUseCase.invoke(_apiSeriePage.value).results
        }
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
            if (genre in value){
                _actualGenre.value = key
            }
        }
        findByGenre()
    }

    /**
     * Busca las películas y series que tienen el género seleccionado
     */
    private fun findByGenre(){
        val temporalList = mutableListOf<MovieOrSerieState>()
        for (i in 0.._movieList.value.size-1){
            if (_actualGenre.value in _movieList.value[i].genres){
                temporalList.add(_movieList.value[i])
            }else if (_actualGenre.value in _serieList.value[i].genres){
                temporalList.add(_serieList.value[i])
            }
        }
        _moviesAndSeriesList.value = temporalList
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
}