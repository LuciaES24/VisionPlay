package com.lespsan543.visionplay.app.ui.states

/**
 * Estado encargado de guardar una lista de estados de plataformas
 */
data class ProviderState(
    val providerList: List<MovieOrSerieProviderState> = emptyList()
)
