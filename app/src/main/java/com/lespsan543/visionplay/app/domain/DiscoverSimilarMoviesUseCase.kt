package com.lespsan543.visionplay.app.domain

import com.lespsan543.visionplay.app.data.AppRepository
import com.lespsan543.visionplay.app.ui.states.MovieOrSerieResponseState

class DiscoverSimilarMoviesUseCase {
    private val appRepository = AppRepository()

    /**
     * Invoca la función del repositorio que extrae la lista de películas similares a otra
     *
     * @return objeto con la información de la búsqueda
     */
    suspend operator fun invoke(movie_id:Int): MovieOrSerieResponseState {
        return appRepository.discoverSimilarMovies(movie_id)
    }
}