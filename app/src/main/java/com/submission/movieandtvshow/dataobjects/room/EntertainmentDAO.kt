package com.submission.movieandtvshow.dataobjects.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.submission.movieandtvshow.dataobjects.Movie
import com.submission.movieandtvshow.dataobjects.TVShow

@Dao
interface EntertainmentDAO {
    @Query("SELECT * FROM movies")
    fun getMovies(): DataSource.Factory<Int, Movie>

    @Query("SELECT * FROM shows")
    fun getShows(): DataSource.Factory<Int, TVShow>

    @Query("SELECT * FROM movies WHERE fav = :fav")
    fun getFavMovie(fav: Boolean): DataSource.Factory<Int, Movie>

    @Query("SELECT * FROM shows WHERE fav = :fav")
    fun getFavShow(fav: Boolean): DataSource.Factory<Int, TVShow>

    @Query("SELECT * FROM movies WHERE id = :showId")
    fun getMovieDetails(showId: String): LiveData<Movie>

    @Query("SELECT * FROM shows WHERE id = :showId")
    fun getShowDetails(showId: String): LiveData<TVShow>

    @Query("UPDATE movies SET fav = :fav WHERE id = :showId")
    fun setFavouriteMovie(fav: Boolean, showId: String)

    @Query("UPDATE shows SET fav = :fav WHERE id = :showId")
    fun setFavouriteShow(fav: Boolean, showId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(show: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShows(show: List<TVShow>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingleShow(show: TVShow)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingleMovie(show: Movie)
}