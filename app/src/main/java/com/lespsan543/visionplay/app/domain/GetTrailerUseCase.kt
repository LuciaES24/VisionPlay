package com.lespsan543.visionplay.app.domain

import com.lespsan543.visionplay.app.data.AppRepository

/**
 * Caso de uso que realiza una búsqueda en la API de un tráiler
 *
 * @property appRepository repositorio del que obtenemos la comunicación con la API
 */
class GetTrailerUseCase {
    private val appRepository = AppRepository()

    /**
     * Invoca la función del repositorio que extrae el id del trailer
     *
     * @param movieOrSerie película o serie formateada para realizar la búsqueda
     *
     * @return id del trailer
     */
    suspend operator fun invoke(movieOrSerie: String): String {
        return appRepository.getYoutubeTrailer(movieOrSerie)
    }
}