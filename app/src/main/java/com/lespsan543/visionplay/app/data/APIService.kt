package com.lespsan543.visionplay.app.data

import com.lespsan543.visionplay.app.data.model.GenresModel
import com.lespsan543.visionplay.app.data.model.MovieResponse
import com.lespsan543.visionplay.app.data.model.SerieResponse
import com.lespsan543.visionplay.app.data.model.YoutubeResponse
import com.lespsan543.visionplay.app.data.model.search.SearchResponse
import com.lespsan543.visionplay.app.data.model.watchProviders.MovieOrSerieProviderResponse
import com.lespsan543.visionplay.app.data.util.Constants.API_KEY
import com.lespsan543.visionplay.app.data.util.Constants.BASE_URL
import com.lespsan543.visionplay.app.data.util.Constants.YOUTUBE_KEY
import com.lespsan543.visionplay.app.data.util.Constants.YOUTUBE_URL
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Define la API y las consultas que se le van a realizar
 * Recoge los datos y los convierte al modelo de datos establecido
 */
class APIService {
    private val retrofitTMDB = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitYoutube = Retrofit.Builder()
        .baseUrl(YOUTUBE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * Recoge una lista de las películas más populares
     *
     * @param page página de la API
     *
     * @return objeto MovieResponse con los datos de las películas encontradas
     */
    suspend fun discoverMovies(page:Int) : Response<MovieResponse>{
        return retrofitTMDB.create(RetrofitAPI::class.java).discoverMovies(API_KEY, page)
    }

    /**
     * Recoge una lista de películas similares a partir del id de otra
     *
     * @param movie_id identificador de la película
     *
     * @return objeto MovieResponse con los datos de las películas encontradas
     */
    suspend fun discoverSimilarMovies(movie_id: Int) : Response<MovieResponse>{
        return retrofitTMDB.create(RetrofitAPI::class.java).discoverSimilarMovies(movie_id, API_KEY)
    }

    /**
     * Recoge una lista de series similares a partir del id de otra
     *
     * @param series_id identificador de la serie
     *
     * @return objeto SerieResponse con los datos de las películas encontradas
     */
    suspend fun discoverSimilarSeries(series_id: Int) : Response<SerieResponse>{
        return retrofitTMDB.create(RetrofitAPI::class.java).discoverSimilarSeries(series_id, API_KEY)
    }

    /**
     * Recoge una lista de películas que se encuentran actualmente en el cine
     *
     * @param page página de la API
     *
     * @return objeto MovieResponse con la información recogida de la API
     */
    suspend fun getCineMovies(page:Int) : Response<MovieResponse> {
        return retrofitTMDB.create(RetrofitAPI::class.java).getCineMovies(API_KEY, page)
    }

    /**
     * Recoge una lista de plataformas en las que se encuentra una película
     *
     * @param movie_id identificador de la película
     *
     * @return objeto MovieOrSerieProviderResponse con la información recogida de la API
     */
    suspend fun getMovieProvider(movie_id:Int) : Response<MovieOrSerieProviderResponse> {
        return retrofitTMDB.create(RetrofitAPI::class.java).discoverMoviePlatform(movie_id,API_KEY)
    }

    /**
     * Recoge una lista de plataformas en las que se encuentra una serie
     *
     * @param series_id identificador de la serie
     *
     * @return objeto MovieOrSerieProviderResponse con la información recogida de la API
     */
    suspend fun getSerieProvider(series_id:Int) : Response<MovieOrSerieProviderResponse> {
        return retrofitTMDB.create(RetrofitAPI::class.java).discoverSeriePlatform(series_id,API_KEY)
    }

    /**
     * Recoge una lista de las series más populares
     *
     * @param page página de la API
     *
     * @return objeto SerieResponse con la información recogida de la API
     */
    suspend fun discoverSeries(page:Int) : Response<SerieResponse> {
        return retrofitTMDB.create(RetrofitAPI::class.java).discoverSeries(API_KEY, page)
    }

    /**
     * Recoge una lista de gérenos de películas
     *
     * @return objeto MovieResponse con la información recogida de la API
     */
    suspend fun discoverMovieGenres() : Response<GenresModel> {
        return retrofitTMDB.create(RetrofitAPI::class.java).getMovieGenres(API_KEY)
    }

    /**
     * Recoge una lista de gérenos de series
     *
     * @return objeto SerieResponse con la información recogida de la API
     */
    suspend fun discoverSerieGenres() : Response<GenresModel> {
        return retrofitTMDB.create(RetrofitAPI::class.java).getSerieGenres(API_KEY)
    }

    /**
     * Recoge una lista de películas y series que coinciden con la búsqueda
     *
     * @param query búsqueda que realiza el usuario
     *
     * @return objeto SearchResponse con la información recogida de la API
     */
    suspend fun searchMovieOrSerie(query:String) : Response<SearchResponse> {
        return retrofitTMDB.create(RetrofitAPI::class.java).searchMovieOrSerie(API_KEY, query)
    }

    /**
     * Recoge una serie de videos que coinciden con la búsqueda
     *
     * @param movieOrSerie título de la película o serie a buscar
     *
     * @return objeto YoutubeResponse con la información recogida de la API
     */
    suspend fun getYoutubeTrailer(movieOrSerie : String) : Response<YoutubeResponse> {
        return retrofitYoutube.create(RetrofitAPI::class.java).getTrailer(YOUTUBE_KEY, movieOrSerie)
    }
}