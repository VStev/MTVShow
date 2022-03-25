package com.submission.movieandtvshow.dataobjects

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @NonNull
    var movieID: String = "",

    @ColumnInfo(name = "title")
    @SerializedName("title")
    var title: String = "",

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    var releaseYear: String = "",

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    var details: String = "",

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    var poster: String = "",

    @ColumnInfo(name = "fav")
    var fav: Boolean = false
)
