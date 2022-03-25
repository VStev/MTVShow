package com.submission.movieandtvshow.dataobjects.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.submission.movieandtvshow.dataobjects.Movie
import com.submission.movieandtvshow.dataobjects.TVShow
import com.submission.movieandtvshow.utilities.AppExecutors
import com.submission.movieandtvshow.vo.Resource
import com.submission.movieandtvshow.webapi.ApiResponse
import com.submission.movieandtvshow.webapi.RetrofitCallback

//singleton obj
class RemoteRepository(
    private val retrofit: RetrofitCallback,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors) {

    companion object {
        @Volatile
        private var instance: RemoteRepository? = null

        fun getInstance(retrofit: RetrofitCallback, localData: LocalDataSource, appExecutors: AppExecutors): RemoteRepository =
            instance ?: synchronized(this) {
                instance ?: RemoteRepository(retrofit, localData, appExecutors).apply { instance = this }
            }
    }

    fun getMovies(): LiveData<Resource<PagedList<Movie>>> {
        return object: NetworkBoundResource<PagedList<Movie>, List<Movie>>(appExecutors){
            override fun loadFromDB(): LiveData<PagedList<Movie>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(60)
                    .setPageSize(20)
                    .build()
                return LivePagedListBuilder(localDataSource.getMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<Movie>?): Boolean = data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<Movie>>> = retrofit.getMovies()

            override fun saveCallResult(data: List<Movie>) {
                val list = ArrayList<Movie>()
                for (movie in data){
                    val item = Movie(
                        movie.movieID,
                        movie.title,
                        movie.releaseYear,
                        movie.details,
                        movie.poster
                    )
                    list.add(item)
                }
                localDataSource.insertMovies(list)
            }
        }.asLiveData()
    }

    fun getShows(): LiveData<Resource<PagedList<TVShow>>> {
        return object: NetworkBoundResource<PagedList<TVShow>, List<TVShow>>(appExecutors){
            override fun loadFromDB(): LiveData<PagedList<TVShow>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(60)
                    .setPageSize(20)
                    .build()
                return LivePagedListBuilder(localDataSource.getShows(), config).build()
            }

            override fun shouldFetch(data: PagedList<TVShow>?): Boolean = data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TVShow>>> = retrofit.getShows()

            override fun saveCallResult(data: List<TVShow>) {
                val list = ArrayList<TVShow>()
                for (show in data){
                    val item = TVShow(
                        show.showID,
                        show.title,
                        show.releaseYear,
                        show.details,
                        show.ongoing,
                        show.episodes,
                        show.seasons,
                        show.poster
                    )
                    list.add(item)
                }
                localDataSource.insertShows(list)
            }
        }.asLiveData()
    }

    fun getShowDetails(showId: String): LiveData<Resource<TVShow>> {
        return object: NetworkBoundResource<TVShow, TVShow>(appExecutors){
            override fun loadFromDB(): LiveData<TVShow> = localDataSource.getShowDetails(showId)

            override fun shouldFetch(data: TVShow?): Boolean = data == null

            override fun createCall(): LiveData<ApiResponse<TVShow>> = retrofit.getShowDetails(showId)

            override fun saveCallResult(data: TVShow) {
                localDataSource.insertSingleShow(data)
            }
        }.asLiveData()
    }

    fun getMovieDetail(showId: String): LiveData<Resource<Movie>> {
        return object: NetworkBoundResource<Movie, Movie>(appExecutors){
            override fun loadFromDB(): LiveData<Movie> = localDataSource.getMovieDetails(showId)

            override fun shouldFetch(data: Movie?): Boolean = data == null

            override fun createCall(): LiveData<ApiResponse<Movie>> = retrofit.getMovieDetail(showId)

            override fun saveCallResult(data: Movie) {
                localDataSource.insertSingleMovie(data)
            }
        }.asLiveData()
    }

    fun getFavouriteMovies(): LiveData<PagedList<Movie>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .setPageSize(20)
            .build()
        return LivePagedListBuilder(localDataSource.getFavMovie(true), config).build()
    }

    fun getFavouriteShows(): LiveData<PagedList<TVShow>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .setPageSize(20)
            .build()
        return LivePagedListBuilder(localDataSource.getFavShow(true), config).build()
    }

    fun setFavouriteMovie(show: String, state: Boolean){
        appExecutors.diskIO().execute { localDataSource.setFavouriteMovie(show, state) }
    }

    fun setFavouriteShow(show: String, state: Boolean){
        appExecutors.diskIO().execute { localDataSource.setFavouriteShow(show, state) }
    }
}