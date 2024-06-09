package com.lespsan543.visionplay.app.data.model.watchProviders

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos que guarda la lista de países en los que hay plataformas
 */
data class MovieOrSerieProviderResponse (
    @SerializedName("results")
    var results : CountryResponse
)