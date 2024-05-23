package com.lespsan543.visionplay.app.data.model.watchProviders

import com.google.gson.annotations.SerializedName

data class TypesModel(
    @SerializedName("buy")
    var buy: List<MovieOrSerieProviderModel>? = null,
    @SerializedName("rent")
    var rent: List<MovieOrSerieProviderModel>? = null,
    @SerializedName("flatrate")
    var flatrate: List<MovieOrSerieProviderModel>? = null
)
