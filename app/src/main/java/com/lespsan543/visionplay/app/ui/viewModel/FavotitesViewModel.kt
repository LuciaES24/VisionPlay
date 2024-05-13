package com.lespsan543.apppeliculas.peliculas.ui.viewModel

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * ViewModel responsable del flujo de datos de películas y series que se añaden a favoritos
 * recogiendo la información de la base de datos
 *
 * @property auth Instancia de FirebaseAuth utilizada para obtener el usuario actual
 * @property firestore Instancia de FirebaseFirestore utilizada para operaciones en la base de datos
 * @property getMovieGenresUseCase caso de uso que busca los géneros de las películas en la API
 * @property getSerieGenresUseCase caso de uso que busca los géneros de las series en la API
 * @property discoverMoviesUseCase caso de uso que busca una lista de películas en la API
 * @property discoverSeriesUseCase caso de uso que busca una lista de series en la API
 * @property _favoritesList flujo de datos de las películas y series que se han añadido a favoritos
 * @property favoritesList estado público de la lista de favoritos
 * @property _selectedMovieOrSerie flujo de datos que guarda la película o serie que se ha seleccionado en la UI
 * @property selectedMovieOrSerie estado público de la película o serie seleccionada
 * @property _showGenres flujo de datos de los géneros que se deben mostrar en pantalla
 * @property showGenres estado público de los géneros a mostrar
 * @property _movieGenres mapa que guarda los géneros a los que puede pertenecer una película
 * @property _serieGenres mapa que guarda los géneros a los que puede pertenecer una serie
 * @property _movieList lista de películas obtenidas de la API
 * @property _serieList lista de series obtenidas de la API
 * @property _dbList lista de películas y series que van a ser guardadas en la base de datos
 */
class FavotitesViewModel :ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore

    private val getMovieGenresUseCase = GetMovieGenresUseCase()

    private val getSerieGenresUseCase = GetSerieGenresUseCase()

    private val discoverMoviesUseCase = DiscoverMoviesUseCase()

    private val discoverSeriesUseCase = DiscoverSeriesUseCase()

    private var _favoritesList = MutableStateFlow<List<MovieOrSerieState>>(emptyList())
    var favoritesList : StateFlow<List<MovieOrSerieState>> = _favoritesList.asStateFlow()

    private val _selectedMovieOrSerie = MutableStateFlow(MovieOrSerieState())
    var selectedMovieOrSerie : StateFlow<MovieOrSerieState> = _selectedMovieOrSerie

    private var _showGenres = MutableStateFlow("")
    var showGenres : StateFlow<String> = _showGenres

    private var _movieGenres = MutableStateFlow<Map<String,String>>(emptyMap())

    private var _serieGenres = MutableStateFlow<Map<String,String>>(emptyMap())

    private var _movieList = MutableStateFlow<List<MovieOrSerieState>>(emptyList())

    private var _serieList = MutableStateFlow<List<MovieOrSerieState>>(emptyList())

    private var _dbList = MutableStateFlow<List<List<MovieOrSerieState>>>(emptyList())

    init {
        movieGenres()
        serieGenres()
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
     * Guarda la película o serie que se ha seleccionado en la variable
     * del ViewModel
     *
     * @param movieOrSerie película o serie que se ha seleccioando
     */
    fun changeSelectedMovieOrSerie(movieOrSerie:MovieOrSerieState){
        _selectedMovieOrSerie.value = movieOrSerie
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
     * Busca todas las películas y series que se han añadido a favoritos a partir
     * del email del usuario que ha iniciado sesión
     */
    fun fetchMoviesAndSeries() {
        val email = auth.currentUser?.email
        firestore.collection("Favoritos")
            .whereEqualTo("emailUser", email.toString())
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val favorites = mutableListOf<MovieOrSerieState>()
                if (querySnapshot != null) {
                    for (document in querySnapshot) {
                        val movieOrSerie = document.toObject(MovieOrSerieState::class.java)
                        movieOrSerie.idDoc = document.id
                        favorites.add(movieOrSerie)
                    }
                }
                _favoritesList.value = favorites
            }
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
                save(movieOrSerie)
            }
        }
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
     * Guarda la película o serie que se le indica en la base de datos
     *
     * @param searchMovieState película o serie que queremos añadir a favoritos
     */
    private fun save(searchMovieState: MovieOrSerieState) {

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
                    "type" to searchMovieState.type
                )
                firestore.collection("MoviesAndSeries")
                    .add(newMovieOrSerie)
                    .addOnSuccessListener {
                    }
                    .addOnFailureListener {
                        throw Exception()
                    }
            } catch (e: Exception){
                print(e.localizedMessage)
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
     * Elimina la película o serie que se le indica de la base de datos de favoritos
     *
     * @param id id de la película o serie que vamos a eliminar
     */
    fun deleteMovieOrSerie(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                firestore.collection("Favoritos").document(id)
                    .delete()
            } catch (e: Exception) {
                print(e.localizedMessage)
            }
        }
    }
}