package com.submission.movieandtvshow.webapi

import com.submission.movieandtvshow.BuildConfig
import com.submission.movieandtvshow.dataobjects.Movie
import com.submission.movieandtvshow.dataobjects.TVShow
import com.submission.movieandtvshow.dataobjects.remote.dataentities.MovieDiscoverContainer
import com.submission.movieandtvshow.dataobjects.remote.dataentities.TVDiscoverContainer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface RetrofitInterfaces {
    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    @GET("discover/movie")
    fun discoverMovie(): Call<MovieDiscoverContainer>

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    @GET("discover/tv")
    fun discoverTv(): Call<TVDiscoverContainer>

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    @GET("movie/{movieId}")
    fun getMovieDetails(@Path("movieId") movieId: String): Call<Movie>

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    @GET("tv/{tvId}")
    fun getShowDetails(@Path("tvId") tvId: String): Call<TVShow>
}