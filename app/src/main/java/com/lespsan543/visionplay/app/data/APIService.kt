package com.lespsan543.visionplay.app.data

import com.lespsan543.visionplay.app.data.model.GenresModel
import com.lespsan543.visionplay.app.data.model.MovieResponse
import com.lespsan543.visionplay.app.data.model.SerieResponse
import com.lespsan543.visionplay.app.data.model.YoutubeResponse
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
     * @return objeto MovieResponse con los datos de las películas encontradas
     */
    suspend fun discoverMovies(page:Int) : Response<MovieResponse>{
        return retrofitTMDB.create(RetrofitAPI::class.java).discoverMovies(API_KEY, page)
    }

    /**
     * Recoge una lista de películas que se encuentran actualmente en el cine
     *
     * @return objeto MovieResponse con la información recogida de la API
     */
    suspend fun getCineMovies(page:Int) : Response<MovieResponse> {
        return retrofitTMDB.create(RetrofitAPI::class.java).getCineMovies(API_KEY, page)
    }

    /**
     * Recoge una lista de las series más populares
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
     * Recoge una serie de videos que coinciden con la búsqueda
     *
     * @return objeto YoutubeResponse con la información recogida de la API
     */
    suspend fun getYoutubeTrailer(movieOrSerie : String) : Response<YoutubeResponse> {
        return retrofitYoutube.create(RetrofitAPI::class.java).getTrailer(YOUTUBE_KEY, movieOrSerie)
    }
}