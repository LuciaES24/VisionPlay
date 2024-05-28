package com.lespsan543.visionplay.app.data.model.search

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("results")
    var results : List<MovieOrSerieSearchModel>? = null
)
