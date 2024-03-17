package com.lespsan543.visionplay.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Recoge información de una búsqueda sobre películas
 */
data class MovieResponse(
    @SerializedName("results")
    var results : List<MovieModel>
)
