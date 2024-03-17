package com.lespsan543.visionplay.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Recoge información de una búsqueda sobre series
 */
data class SerieResponse(
    @SerializedName("results")
    var results : List<SerieModel>
)
