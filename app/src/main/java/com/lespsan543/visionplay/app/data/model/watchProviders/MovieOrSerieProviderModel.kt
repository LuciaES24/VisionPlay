package com.lespsan543.visionplay.app.data.model.watchProviders

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos que guarda la información de una plataforma
 */
data class MovieOrSerieProviderModel(
    @SerializedName("logo_path")
    var logo : String,
    @SerializedName("provider_id")
    var provider_id : Int,
    @SerializedName("provider_name")
    var provider_name : String
)
