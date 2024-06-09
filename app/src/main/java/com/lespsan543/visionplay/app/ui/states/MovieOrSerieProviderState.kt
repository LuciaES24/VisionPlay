package com.lespsan543.visionplay.app.ui.states

/**
 * Estado encargado de guardar la información de una plataforma
 */
data class MovieOrSerieProviderState (
    var logo : String = "",
    var provider_id : Int = 0,
    var provider_name : String = ""
)