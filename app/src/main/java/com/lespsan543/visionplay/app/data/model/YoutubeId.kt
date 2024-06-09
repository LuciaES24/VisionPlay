package com.lespsan543.visionplay.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos que guarda el identificador de un vídeo de la API de YouTube
 */
data class YoutubeId(
    @SerializedName("videoId")
    val videoId : String
)