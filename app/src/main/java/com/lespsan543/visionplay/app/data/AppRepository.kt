package com.lespsan543.visionplay.app.data

import com.lespsan543.visionplay.app.data.model.MovieModel
import com.lespsan543.visionplay.app.data.model.MovieResponse
import com.lespsan543.visionplay.app.data.model.SerieModel
import com.lespsan543.visionplay.app.data.model.SerieResponse
import com.lespsan543.visionplay.app.data.util.Constants.BASE_URL_IMG
import com.lespsan543.visionplay.app.ui.states.MovieOrSerieResponseState
import com.lespsan543.visionplay.app.ui.states.MovieOrSerieState

/**
 * Repositorio encargado de la comunicación y transformación de datos obtenidos de la API
 *
 * @property apiService servicio del que obtenemos las funciones para acceder a la API
 */
class AppRepository {
    private val apiService = APIService()

    /**
     * Realiza una búsqueda de películas en la API
     *
     * @return resultado de la búsqueda
     */
    suspend fun discoverMovies(page:Int): MovieOrSerieResponseState {
        val response = apiService.discoverMovies(page)
        return if (response.isSuccessful) {
            response.body()?.toMovieOrSerieResponseState() ?: MovieOrSerieResponseState()
        } else {
            MovieOrSerieResponseState()
        }
    }

    /**
     * Realiza una búsqueda de series en la API
     *
     * @return resultado de la búsqueda
     */
    suspend fun discoverSeries(page:Int): MovieOrSerieResponseState {
        val response = apiService.discoverSeries(page)
        return if (response.isSuccessful) {
            response.body()?.toMovieOrSerieResponseState() ?: MovieOrSerieResponseState()
        } else {
            MovieOrSerieResponseState()
        }
    }

    /**
     * Realiza una búsqueda de películas en la API
     *
     * @return resultado de la búsqueda
     */
    suspend fun getCineMovies(page:Int): MovieOrSerieResponseState {
        val response = apiService.getCineMovies(page)
        return if (response.isSuccessful) {
            response.body()?.toMovieOrSerieResponseState() ?: MovieOrSerieResponseState()
        } else {
            MovieOrSerieResponseState()
        }
    }

    /**
     * Realiza una búsqueda de los géneros de películas
     *
     * @return lista con los distintos géneros
     */
    suspend fun getMovieGenres() : MutableMap<String,String>{
        val response = apiService.discoverMovieGenres()
        val genres : MutableMap<String,String> = mutableMapOf()
        if (response.isSuccessful){
            for (i in response.body()!!.genreModels){
                genres[i.id] = i.genre
            }
        }
        return genres
    }

    /**
     * Realiza una búsqueda de los géneros de series
     *
     * @return lista con los distintos géneros
     */
    suspend fun getSerieGenres() : MutableMap<String,String>{
        val response = apiService.discoverSerieGenres()
        val genres : MutableMap<String,String> = mutableMapOf()
        if (response.isSuccessful){
            for (i in response.body()!!.genreModels){
                genres[i.id] = i.genre
            }
        }
        return genres
    }

    /**
     * Realiza una búsqueda en youtube dependiendo del título que se indique
     *
     * @param movieOrSerie título de la película o serie formateado para la búsqueda
     *
     * @return id del video encontrado
     */
    suspend fun getYoutubeTrailer(movieOrSerie: String): String {
        val response = apiService.getYoutubeTrailer(movieOrSerie)
        var id = ""
        if (response.isSuccessful){
            id = response.body()?.items?.get(0)?.id?.videoId.toString()
        }
        return id
    }

    /**
     * Función de extensión que transforma el modelo de datos al estado
     *
     * @return estado con los datos de una búsqueda de películas
     */
    private fun MovieResponse.toMovieOrSerieResponseState(): MovieOrSerieResponseState {
        return MovieOrSerieResponseState(
            results = this.results.map { it.toMovieOrSerieState() }
        )
    }

    /**
     * Función de extensión que transforma el modelo de datos al estado
     *
     * @return estado con los datos de una película
     */
    private fun MovieModel.toMovieOrSerieState(): MovieOrSerieState {
        return MovieOrSerieState(
            title = this.title,
            overview = this.overview,
            poster = BASE_URL_IMG + this.poster,
            date = this.date,
            votes = this.votes,
            genres = this.genres,
            type = "Movie"
        )
    }

    /**
     * Función de extensión que transforma el modelo de datos al estado
     *
     * @return estado con los datos de una búsqueda de series
     */
    private fun SerieResponse.toMovieOrSerieResponseState(): MovieOrSerieResponseState {
        return MovieOrSerieResponseState(
            results = this.results.map { it.toMovieOrSerieState() }
        )
    }

    /**
     * Función de extensión que transforma el modelo de datos al estado
     *
     * @return estado con los datos de una serie
     */
    private fun SerieModel.toMovieOrSerieState(): MovieOrSerieState {
        return MovieOrSerieState(
            title = this.name,
            overview = this.overview,
            poster = BASE_URL_IMG + this.poster,
            date = this.date,
            votes = this.votes,
            genres = this.genres,
            type = "Serie"
        )
    }
}