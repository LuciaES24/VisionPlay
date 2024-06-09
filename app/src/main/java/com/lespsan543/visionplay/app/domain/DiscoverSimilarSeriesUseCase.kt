package com.lespsan543.visionplay.app.domain

import com.lespsan543.visionplay.app.data.AppRepository
import com.lespsan543.visionplay.app.ui.states.MovieOrSerieResponseState

class DiscoverSimilarSeriesUseCase {
    private val appRepository = AppRepository()

    /**
     * Invoca la función del repositorio que extrae la lista de películas y series similares a otra
     *
     * @param serie_id identificador de la serie
     *
     * @return objeto con la información de la búsqueda
     */
    suspend operator fun invoke(serie_id:Int): MovieOrSerieResponseState {
        return appRepository.discoverSimilarSeries(serie_id)
    }
}