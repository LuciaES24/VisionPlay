package com.lespsan543.visionplay.app.data

import com.lespsan543.visionplay.app.data.model.Genres
import com.lespsan543.visionplay.app.data.model.MovieResponse
import com.lespsan543.visionplay.app.data.model.SerieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Interfaz que determina las operaciones que se le realizarán a la API
 */
interface RetrofitAPI {
    /**
     * Recoge información de la API
     *
     * @param url url de la API en la que queremos buscar
     *
     * @return respuesta con los resultados obtenidos
     */
    @GET("discover/movie")
    suspend fun discoverMovies(@Query("api_key") apiKey:String, @Query("page") page:Int) : Response<MovieResponse>

    @GET("movie/now_playing")
    suspend fun getCineMovies(@Query("api_key") apiKey:String, @Query("page") page:Int) : Response<MovieResponse>

    @GET("tv/popular")
    suspend fun discoverSeries(@Query("api_key") apiKey:String, @Query("page") page:Int) : Response<SerieResponse>

    @GET("genre/movie/list")
    suspend fun getMovieGenres(@Query("api_key") key:String) : Response<Genres>

    @GET("genre/tv/list")
    suspend fun getSerieGenres(@Query("api_key") key:String) : Response<Genres>

}