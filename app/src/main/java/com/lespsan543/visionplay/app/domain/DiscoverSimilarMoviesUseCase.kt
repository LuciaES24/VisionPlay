package com.lespsan543.visionplay.app.domain

import com.lespsan543.visionplay.app.data.AppRepository
import com.lespsan543.visionplay.app.ui.states.MovieOrSerieResponseState

/**
 * Caso de uso que realiza una búsqueda en la API de películas y series similares a la seleccionada
 *
 * @property appRepository repositorio del que obtenemos la comunicación con la API
 */
class DiscoverSimilarMoviesUseCase {
    private val appRepository = AppRepository()

    /**
     * Invoca la función del repositorio que extrae la lista de películas y series similares a otra
     *
     * @param movie_id identificador de la película
     *
     * @return objeto con la información de la búsqueda
     */
    suspend operator fun invoke(movie_id:Int): MovieOrSerieResponseState {
        return appRepository.discoverSimilarMovies(movie_id)
    }
}