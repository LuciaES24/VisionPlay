package com.lespsan543.visionplay.app.data.model.watchProviders

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos que guarda las listas de los distintos tipos de plataformas que hay
 */
data class TypesModel(
    @SerializedName("buy")
    var buy: List<MovieOrSerieProviderModel>? = null,
    @SerializedName("rent")
    var rent: List<MovieOrSerieProviderModel>? = null,
    @SerializedName("flatrate")
    var flatrate: List<MovieOrSerieProviderModel>? = null
)
