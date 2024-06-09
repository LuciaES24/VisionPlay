package com.lespsan543.visionplay.app.data.model.search

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos que guarda la lista de resultados de una búsqueda
 */
data class SearchResponse(
    @SerializedName("results")
    var results : List<MovieOrSerieSearchModel>? = null
)
