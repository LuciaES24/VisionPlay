package com.lespsan543.visionplay.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos que guarda la lista de géneros obtenidos en una búsqueda
 */
data class GenresModel(
    @SerializedName("genres")
    var genreModels : List<GenreModel>
)
