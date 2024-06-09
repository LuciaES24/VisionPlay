package com.lespsan543.visionplay.app.data.model.search

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos que guarda la información de una película o serie
 */
data class MovieOrSerieSearchModel(
    @SerializedName("id")
    var idAPI: Int? = null,
    @SerializedName("original_name")
    var titleTv: String? = null,
    @SerializedName("original_title")
    var title: String? = null,
    @SerializedName("overview")
    var overview: String? = null,
    @SerializedName("media_type")
    var type : String? = null,
    @SerializedName("poster_path")
    var poster: String? = null,
    @SerializedName("release_date")
    var date: String? = null,
    @SerializedName("vote_average")
    var votes: String? = null,
    @SerializedName("genre_ids")
    var genres : List<String>? = null,
    @SerializedName("first_air_date")
    var dateTv: String? = null,
    @SerializedName("popularity")
    var popularity : Double? = null
)
