package com.lespsan543.visionplay.app.data.model

import com.google.gson.annotations.SerializedName

data class Genres(
    @SerializedName("genres")
    var genres : List<Genre>
)
