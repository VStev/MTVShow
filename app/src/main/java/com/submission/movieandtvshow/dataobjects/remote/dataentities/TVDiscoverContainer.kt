package com.submission.movieandtvshow.dataobjects.remote.dataentities

import com.google.gson.annotations.SerializedName
import com.submission.movieandtvshow.dataobjects.TVShow

data class TVDiscoverContainer (
    @SerializedName("results")
    val result:List<TVShow>
)