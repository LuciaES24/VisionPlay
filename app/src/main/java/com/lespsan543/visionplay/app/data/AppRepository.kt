package com.lespsan543.visionplay.app.data

import com.lespsan543.visionplay.app.data.model.MovieModel
import com.lespsan543.visionplay.app.data.model.MovieResponse
import com.lespsan543.visionplay.app.data.model.SerieModel
import com.lespsan543.visionplay.app.data.model.SerieResponse
import com.lespsan543.visionplay.app.data.model.search.MovieOrSerieSearchModel
import com.lespsan543.visionplay.app.data.model.search.SearchResponse
import com.lespsan543.visionplay.app.data.model.watchProviders.CountryResponse
import com.lespsan543.visionplay.app.data.model.watchProviders.MovieOrSerieProviderModel
import com.lespsan543.visionplay.app.data.util.Constants.BASE_URL_IMG
import com.lespsan543.visionplay.app.ui.states.MovieOrSerieProviderState
import com.lespsan543.visionplay.app.ui.states.MovieOrSerieResponseState
import com.lespsan543.visionplay.app.ui.states.MovieOrSerieState
import com.lespsan543.visionplay.app.ui.states.ProviderState

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
     * @param page página de la API
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
     * @param page página de la API
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
     * Realiza una búsqueda de películas y series similares en la API
     *
     * @param movie_id identificador de la película
     *
     * @return resultado de la búsqueda
     */
    suspend fun discoverSimilarMovies(movie_id:Int): MovieOrSerieResponseState {
        val response = apiService.discoverSimilarMovies(movie_id)
        return if (response.isSuccessful) {
            response.body()?.toMovieOrSerieResponseState() ?: MovieOrSerieResponseState()
        } else {
            MovieOrSerieResponseState()
        }
    }

    /**
     * Realiza una búsqueda de películas y series similares en la API
     *
     * @param series_id identificador de la serie
     *
     * @return resultado de la búsqueda
     */
    suspend fun discoverSimilarSeries(series_id: Int): MovieOrSerieResponseState {
        val response = apiService.discoverSimilarSeries(series_id)
        return if (response.isSuccessful) {
            response.body()?.toMovieOrSerieResponseState() ?: MovieOrSerieResponseState()
        } else {
            MovieOrSerieResponseState()
        }
    }

    /**
     * Realiza una búsqueda de películas en la API
     *
     * @param page página de la API
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
     * Realiza una búsqueda de películas y series en la API según la consulta del usuario
     *
     * @param query búsqueda que realiza el usuario
     *
     * @return resultado de la búsqueda
     */
    suspend fun searchMovieOrSerie(query:String): MovieOrSerieResponseState {
        val response = apiService.searchMovieOrSerie(query)
        return if (response.isSuccessful) {
            response.body()?.toMovieOrSerieResponseState() ?: MovieOrSerieResponseState()
        } else {
            MovieOrSerieResponseState()
        }
    }

    /**
     * Realiza una búsqueda de los géneros de películas
     *
     * @return mapa con los distintos géneros
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
     * @return mapa con los distintos géneros
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
     * Realiza una búsqueda de las plataformas en las que se encuentra una película
     *
     * @param movie_id identificador de la película a buscar
     *
     * @return modelo de datos con la lista de plataformas obtenida
     */
    suspend fun getMovieProvider(movie_id: Int): ProviderState {
        val response = apiService.getMovieProvider(movie_id)
        var result = ProviderState()
        if (response.isSuccessful){
            result = response.body()!!.results.toProviderState()
        }
        return result
    }

    /**
     * Realiza una búsqueda de las plataformas en las que se encuentra una serie
     *
     * @param series_id identificador de la serie a buscar
     *
     * @return modelo de datos con la lista de plataformas obtenida
     */
    suspend fun getSerieProvider(series_id: Int): ProviderState {
        val response = apiService.getSerieProvider(series_id)
        var result = ProviderState()
        if (response.isSuccessful && response.body() != null){
            result = response.body()!!.results.toProviderState()
        }
        return result
    }

    /**
     * Función de extensión que transforma el modelo de datos al estado
     *
     * @return estado con los datos de búsqueda de las plataformas donde ver una película o serie
     */
    private fun CountryResponse.toProviderState() : ProviderState{
        val platforms = (this.result?.buy ?: emptyList()) + (this.result?.flatrate ?: emptyList()) + (this.result?.rent
            ?: emptyList())
        val distinctPlatforms = platforms.distinct()
        return ProviderState(
            providerList = distinctPlatforms.map { it.toMovieOrSerieProviderState() }
        )
    }

    /**
     * Función de extensión que transforma el modelo de datos al estado
     *
     * @return estado con los datos de búsqueda de la plataforma donde ver una película o serie
     */
    private fun MovieOrSerieProviderModel.toMovieOrSerieProviderState() : MovieOrSerieProviderState{
        return MovieOrSerieProviderState(
            logo = BASE_URL_IMG + this.logo,
            provider_id = this.provider_id,
            provider_name = this.provider_name
        )
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
     * @return estado con los datos de una búsqueda de películas y series
     */
    private fun SearchResponse.toMovieOrSerieResponseState(): MovieOrSerieResponseState {
        return MovieOrSerieResponseState(
            results = this.results!!.filter { (it.type == "movie" && it.poster != null && !it.overview.isNullOrEmpty()) || (it.type == "tv" && it.poster != null && !it.overview.isNullOrEmpty()) }.sortedByDescending { it.popularity }.map { it.toMovieOrSerieState() }
        )
    }

    /**
     * Función de extensión que transforma el modelo de datos al estado
     *
     * @return estado con los datos de una película
     */
    private fun MovieOrSerieSearchModel.toMovieOrSerieState(): MovieOrSerieState {
        return MovieOrSerieState(
            idAPI = this.idAPI!!,
            title = this.title ?: this.titleTv!!,
            overview = this.overview!!,
            poster = BASE_URL_IMG + this.poster,
            date = this.date ?: this.dateTv ?: "",
            votes = this.votes!!,
            genres = this.genres!!,
            type = this.type!!
        )
    }

    /**
     * Función de extensión que transforma el modelo de datos al estado
     *
     * @return estado con los datos de una película
     */
    private fun MovieModel.toMovieOrSerieState(): MovieOrSerieState {
        return MovieOrSerieState(
            idAPI = this.idAPI,
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
            idAPI = this.idAPI,
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