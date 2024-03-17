package com.lespsan543.visionplay.app.data.model

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    var id : String,
    @SerializedName("name")
    var genre : String
)
