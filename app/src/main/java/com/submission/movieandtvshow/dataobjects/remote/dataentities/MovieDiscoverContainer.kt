package com.submission.movieandtvshow.dataobjects.remote.dataentities

import com.google.gson.annotations.SerializedName
import com.submission.movieandtvshow.dataobjects.Movie

data class MovieDiscoverContainer(
    @SerializedName("results")
    val result:List<Movie>
)
