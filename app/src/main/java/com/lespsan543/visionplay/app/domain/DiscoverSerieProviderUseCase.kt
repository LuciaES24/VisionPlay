package com.lespsan543.visionplay.app.domain

import com.lespsan543.visionplay.app.data.AppRepository
import com.lespsan543.visionplay.app.ui.states.ProviderState

/**
 * Caso de uso que realiza una búsqueda en la API de las plataformas en las que se encuentra una serie
 *
 * @property appRepository repositorio del que obtenemos la comunicación con la API
 */
class DiscoverSerieProviderUseCase {
    private val appRepository = AppRepository()

    /**
     * Invoca la función del repositorio que extrae la lista de paltaformas de una serie
     *
     * @param series_id identificador de la película o serie
     *
     * @return objeto con la información de la búsqueda
     */
    suspend operator fun invoke(series_id:Int): ProviderState {
        return appRepository.getSerieProvider(series_id)
    }
}