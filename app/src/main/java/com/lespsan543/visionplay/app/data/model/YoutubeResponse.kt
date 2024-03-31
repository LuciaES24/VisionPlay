package com.lespsan543.visionplay.app.data.model

import com.google.gson.annotations.SerializedName

data class YoutubeResponse(
    @SerializedName("items")
    val items : List<YoutubeItems>
)