package com.lespsan543.visionplay.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos que guarda la información de un género
 */
data class GenreModel(
    @SerializedName("id")
    var id : String,
    @SerializedName("name")
    var genre : String
)
