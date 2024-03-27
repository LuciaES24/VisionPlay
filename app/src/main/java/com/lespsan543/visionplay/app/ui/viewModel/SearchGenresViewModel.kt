package com.lespsan543.visionplay.app.ui.viewModel

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
import kotlinx.coroutines.launch

class SearchGenresViewModel : ViewModel(){
    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore

    private val getMovieGenres = GetMovieGenres()

    private val getSerieGenres = GetSerieGenres()

    private var _moviesInDB = MutableStateFlow<List<MovieOrSerieState>>(emptyList())

    private var _movieGenres = MutableStateFlow<Map<String,String>>(emptyMap())

    private var _serieGenres = MutableStateFlow<Map<String,String>>(emptyMap())

    private var _genres = MutableStateFlow<Map<String,String>>(emptyMap())

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
     * Buscamos los géneros que hay entre películas y series
     * para que no se repitan si alguno coincide
     * y los guardamos en la variable _genres
     */
    private fun diferentGenres(){
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
    }
}