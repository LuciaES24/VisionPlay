package com.lespsan543.visionplay.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos que guarda el resultado de la API de YouTube
 */
data class YoutubeItems(
    @SerializedName("id")
    val id : YoutubeId
)