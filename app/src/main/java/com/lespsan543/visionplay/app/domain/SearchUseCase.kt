package com.lespsan543.visionplay.app.domain

import com.lespsan543.visionplay.app.data.AppRepository
import com.lespsan543.visionplay.app.ui.states.MovieOrSerieResponseState

class SearchUseCase {
    private val appRepository = AppRepository()

    /**
     * Invoca la función del repositorio que extrae los resultados de una búsqueda
     *
     * @param query búsqueda que realiza el usuario
     *
     * @return lista de resultados
     */
    suspend operator fun invoke(query: String): MovieOrSerieResponseState {
        return appRepository.searchMovieOrSerie(query)
    }
}