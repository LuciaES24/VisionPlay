package com.lespsan543.visionplay.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos que guarda la lista de resultados de la API de YouTube
 */
data class YoutubeResponse(
    @SerializedName("items")
    val items : List<YoutubeItems>
)