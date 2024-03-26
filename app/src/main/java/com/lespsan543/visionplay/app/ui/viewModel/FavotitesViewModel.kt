package com.lespsan543.apppeliculas.peliculas.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lespsan543.visionplay.app.domain.GetMovieGenres
import com.lespsan543.visionplay.app.domain.GetSerieGenres
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
 * @property _favoritesList flujo de datos de las películas y series que se han añadido a favoritos
 * @property favoritesList estado público de la lista de favoritos
 * @property _selectedMovieOrSerie flujo de datos que guarda la película o serie que se ha seleccionado en la UI
 * @property selectedMovieOrSerie estado público de la película o serie seleccionada
 */
class FavotitesViewModel :ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore

    private val getMovieGenres = GetMovieGenres()

    private val getSerieGenres = GetSerieGenres()

    private var _favoritesList = MutableStateFlow<List<MovieOrSerieState>>(emptyList())
    var favoritesList : StateFlow<List<MovieOrSerieState>> = _favoritesList.asStateFlow()

    private val _selectedMovieOrSerie = MutableStateFlow(MovieOrSerieState())
    var selectedMovieOrSerie : StateFlow<MovieOrSerieState> = _selectedMovieOrSerie

    private var _showGenres = MutableStateFlow("")
    var showGenres : StateFlow<String> = _showGenres

    private var _movieGenres = MutableStateFlow<Map<String,String>>(emptyMap())

    private var _serieGenres = MutableStateFlow<Map<String,String>>(emptyMap())

    init {
        movieGenres()
        serieGenres()
    }

    /**
     * Buscamos los distintos géneros de las películas
     */
    private fun movieGenres(){
        viewModelScope.launch(Dispatchers.IO) {
            _movieGenres.value = getMovieGenres.invoke()
        }
    }

    /**
     * Buscamos los distintos géneros de las series
     */
    private fun serieGenres(){
        viewModelScope.launch(Dispatchers.IO) {
            _serieGenres.value = getSerieGenres.invoke()
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
     * Elimina la película o serie que se le indica de la base de datos de favoritos
     *
     * @param id id de la película o serie que vamos a eliminar
     */
    fun deleteMovieOrSerie(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                firestore.collection("Favoritos").document(id)
                    .delete()
                    .addOnSuccessListener {
                        Log.d("ELIMINAR OK", "Se eliminó la nota correctamente en Firestore")
                    }
                    .addOnFailureListener {
                        Log.d("ERROR AL ELIMINAR", "ERROR al eliminar una nota en Firestore")
                    }
            } catch (e: Exception) {
                Log.d("ERROR BORRAR NOTA","Error al eliminar ${e.localizedMessage} ")
            }
        }
    }
}