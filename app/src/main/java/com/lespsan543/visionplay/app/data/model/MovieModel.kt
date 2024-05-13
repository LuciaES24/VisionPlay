package com.lespsan543.visionplay.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Guarda la información de una película en concreto
 */
data class MovieModel(
    @SerializedName("id")
    var idAPI: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("poster_path")
    var poster: String,
    @SerializedName("release_date")
    var date: String,
    @SerializedName("vote_average")
    var votes: String,
    @SerializedName("genre_ids")
    var genres : List<String>
)
