package com.lespsan543.visionplay.app.domain

import com.lespsan543.visionplay.app.data.AppRepository
import com.lespsan543.visionplay.app.ui.states.MovieOrSerieResponseState

/**
 * Caso de uso que realiza una búsqueda en la API de las películas que se encuentran en el cine actualmente
 *
 * @property appRepository repositorio del que obtenemos la comunicación con la API
 */
class GetCinemaUseCase {
    private val appRepository = AppRepository()

    /**
     * Invoca la función del repositorio que extrae la lista de películas que se encuentran en el cine
     *
     * @param page página de la API que se debe buscar
     *
     * @return objeto con la información de la búsqueda
     */
    suspend operator fun invoke(page:Int): MovieOrSerieResponseState {
        return appRepository.getCineMovies(page)
    }
}