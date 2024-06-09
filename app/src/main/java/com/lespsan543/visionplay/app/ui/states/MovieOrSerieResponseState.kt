package com.lespsan543.visionplay.app.ui.states

/**
 * Estado encargado de guardar una lista de estados de películas y series
 */
data class MovieOrSerieResponseState(
    var results : List<MovieOrSerieState> = emptyList()
)