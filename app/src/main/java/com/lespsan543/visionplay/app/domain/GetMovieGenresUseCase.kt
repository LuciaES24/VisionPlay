package com.lespsan543.visionplay.app.domain

import com.lespsan543.visionplay.app.data.AppRepository

/**
 * Caso de uso que realiza una búsqueda en la API de los géneros de las películas
 *
 * @property appRepository repositorio del que obtenemos la comunicación con la API
 */
class GetMovieGenresUseCase {
    private val appRepository = AppRepository()

    /**
     * Invoca la función del repositorio que extrae la lista de géneros de películas
     *
     * @return objeto con la información de la búsqueda
     */
    suspend operator fun invoke(): MutableMap<String,String> {
        return appRepository.getMovieGenres()
    }
}