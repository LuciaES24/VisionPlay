package com.lespsan543.visionplay.app.data.model

import com.google.gson.annotations.SerializedName

data class GenresModel(
    @SerializedName("genres")
    var genreModels : List<GenreModel>
)
