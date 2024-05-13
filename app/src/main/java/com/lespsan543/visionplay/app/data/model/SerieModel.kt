package com.lespsan543.visionplay.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Guarda la información de una serie en concreto
 */
data class SerieModel(
    @SerializedName("id")
    var idAPI: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("poster_path")
    var poster: String,
    @SerializedName("first_air_date")
    var date: String,
    @SerializedName("vote_average")
    var votes: String,
    @SerializedName("genre_ids")
    var genres : List<String>
)
