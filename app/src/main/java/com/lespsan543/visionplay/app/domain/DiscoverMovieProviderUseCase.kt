package com.lespsan543.visionplay.app.domain

import com.lespsan543.visionplay.app.data.AppRepository
import com.lespsan543.visionplay.app.ui.states.ProviderState

class DiscoverMovieProviderUseCase {
    private val appRepository = AppRepository()

    /**
     * Invoca la función del repositorio que extrae la lista de paltaformas de una película
     *
     * @return objeto con la información de la búsqueda
     */
    suspend operator fun invoke(movie_id:Int): ProviderState {
        return appRepository.getMovieProvider(movie_id)
    }
}