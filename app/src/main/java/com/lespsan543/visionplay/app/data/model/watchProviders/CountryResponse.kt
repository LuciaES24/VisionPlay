package com.lespsan543.visionplay.app.data.model.watchProviders

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos que guarda el tipo de plataformas que hay disponibles
 */
data class CountryResponse(
    @SerializedName("ES")
    var result : TypesModel? = null
)
