package com.submission.movieandtvshow.dataobjects

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "shows")
data class TVShow(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @NonNull
    var showID: String = "",

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var title: String = "",

    @ColumnInfo(name = "first_air_date")
    @SerializedName("first_air_date")
    var releaseYear: String = "",

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    var details: String = "",

    @ColumnInfo(name = "in_production")
    @SerializedName("in_production")
    var ongoing: Boolean? = false,

    @ColumnInfo(name = "number_of_episodes")
    @SerializedName("number_of_episodes")
    var episodes: Int? = 0,

    @ColumnInfo(name = "number_of_seasons")
    @SerializedName("number_of_seasons")
    var seasons: Int? = 1,

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    var poster: String = "",

    @ColumnInfo(name = "fav")
    var fav: Boolean = false
)
