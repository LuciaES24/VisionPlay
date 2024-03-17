package com.lespsan543.visionplay.app.domain

import com.lespsan543.visionplay.app.data.AppRepository
import com.lespsan543.visionplay.app.ui.states.MovieOrSerieResponseState

class DiscoverMoviesUseCase {
    private val appRepository = AppRepository()

    /**
     * Invoca la función del repositorio que extrae la lista de películas
     *
     * @return objeto con la información de la búsqueda
     */
    suspend operator fun invoke(page:Int): MovieOrSerieResponseState {
        return appRepository.discoverMovies(page)
    }
}