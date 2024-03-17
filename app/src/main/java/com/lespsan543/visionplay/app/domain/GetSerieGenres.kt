package com.lespsan543.visionplay.app.domain

import com.lespsan543.visionplay.app.data.AppRepository

class GetSerieGenres {
    private val appRepository = AppRepository()

    /**
     * Invoca la función del repositorio que extrae la lista de géneros de series
     *
     * @return objeto con la información de la búsqueda
     */
    suspend operator fun invoke(): MutableMap<String,String> {
        return appRepository.getSerieGenres()
    }
}