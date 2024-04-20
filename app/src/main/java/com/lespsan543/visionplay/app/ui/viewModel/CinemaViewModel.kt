package com.lespsan543.visionplay.app.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lespsan543.visionplay.app.domain.GetCinemaUseCase
import com.lespsan543.visionplay.app.domain.GetTrailerUseCase
import com.lespsan543.visionplay.app.ui.states.MovieOrSerieState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class CinemaViewModel : ViewModel(){
    private val getCinemaUseCase = GetCinemaUseCase()

    private val getTrailerUseCase = GetTrailerUseCase()

    private val _cinemaList = MutableStateFlow<List<MovieOrSerieState>>(emptyList())
    val cinemaList : StateFlow<List<MovieOrSerieState>> = _cinemaList

    private val _apiPage = MutableStateFlow(1)

    private val _showTrailer = MutableStateFlow(false)
    val showTrailer : StateFlow<Boolean> = _showTrailer

    private var _trailerId = MutableStateFlow("")
    var trailerId : StateFlow<String> = _trailerId

    init {
        getCinemaMovies()
    }

    /**
     * Buscamos una lista de películas en la API
     */
    private fun getCinemaMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            _cinemaList.value = getCinemaUseCase.invoke(_apiPage.value).results
        }
    }

    /**
     * Calcula la puntuación de una película del 1 al 5
     *
     * @param movie película de la que vamos a obtener la puntuación
     *
     * @return puntuación
     */
    fun calculateVotes(movie: MovieOrSerieState) : Int{
        val votes = movie.votes.toDouble() / 2
        return votes.roundToInt()
    }

    /**
     * Muestra en la pantalla el trailer de la película seleccionada o cierra el diálogo
     */
    fun showMovieTrailer(){
        _showTrailer.value = !_showTrailer.value
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