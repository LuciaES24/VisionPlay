package com.lespsan543.visionplay.app.data

import com.lespsan543.visionplay.app.data.model.GenresModel
import com.lespsan543.visionplay.app.data.model.MovieResponse
import com.lespsan543.visionplay.app.data.model.SerieResponse
import com.lespsan543.visionplay.app.data.model.YoutubeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interfaz que determina las operaciones que se le realizarán a la API
 */
interface RetrofitAPI {
    @GET("discover/movie")
    suspend fun discoverMovies(@Query("api_key") apiKey:String, @Query("page") page:Int) : Response<MovieResponse>

    @GET("movie/{movie_id}/similar")
    suspend fun discoverSimilarMovies(@Path("movie_id") movie_id:Int, @Query("api_key") apiKey:String) : Response<MovieResponse>

    @GET("movie/now_playing")
    suspend fun getCineMovies(@Query("api_key") apiKey:String, @Query("page") page:Int) : Response<MovieResponse>

    @GET("discover/tv")
    suspend fun discoverSeries(@Query("api_key") apiKey:String, @Query("page") page:Int) : Response<SerieResponse>

    @GET("genre/movie/list")
    suspend fun getMovieGenres(@Query("api_key") key:String) : Response<GenresModel>

    @GET("genre/tv/list")
    suspend fun getSerieGenres(@Query("api_key") key:String) : Response<GenresModel>

    @GET("search?part=snippet&")
    suspend fun getTrailer(@Query("key") key: String, @Query("q") query: String) : Response<YoutubeResponse>
}