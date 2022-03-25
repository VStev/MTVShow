package com.submission.movieandtvshow.webapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.submission.movieandtvshow.dataobjects.Movie
import com.submission.movieandtvshow.dataobjects.TVShow
import com.submission.movieandtvshow.dataobjects.remote.dataentities.MovieDiscoverContainer
import com.submission.movieandtvshow.dataobjects.remote.dataentities.TVDiscoverContainer
import com.submission.movieandtvshow.utilities.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitCallback(private val retrofit: RetrofitGenerator) {

    companion object {
        @Volatile
        private var instance: RetrofitCallback? = null

        fun getInstance(retrofit: RetrofitGenerator): RetrofitCallback =
            instance ?: synchronized(this) {
                instance ?: RetrofitCallback(retrofit).apply { instance = this }
            }
    }

    fun getMovies(): LiveData<ApiResponse<List<Movie>>> {
        EspressoIdlingResource.increment()
        val returnValue = MutableLiveData<ApiResponse<List<Movie>>>()
        val call = retrofit.tvMovieRetrofit().create(RetrofitInterfaces::class.java).discoverMovie()
        call.enqueue(object : Callback<MovieDiscoverContainer> {
            override fun onResponse(
                call: Call<MovieDiscoverContainer>,
                response: Response<MovieDiscoverContainer>
            ) {
                if (response.isSuccessful){
                    returnValue.postValue(response.body()?.let { ApiResponse.success(it.result) })
                }
            }

            override fun onFailure(call: Call<MovieDiscoverContainer>, t: Throwable) {

            }
        })
        EspressoIdlingResource.decrement()
        return returnValue
    }

    fun getShows(): LiveData<ApiResponse<List<TVShow>>> {
        EspressoIdlingResource.increment()
        val returnValue = MutableLiveData<ApiResponse<List<TVShow>>>()
        val call = retrofit.tvMovieRetrofit().create(RetrofitInterfaces::class.java).discoverTv()
        call.enqueue(object : Callback<TVDiscoverContainer> {
            override fun onResponse(
                call: Call<TVDiscoverContainer>,
                response: Response<TVDiscoverContainer>
            ) {
                if (response.isSuccessful){
                    returnValue.postValue(response.body()?.let { ApiResponse.success(it.result) })
                }
            }

            override fun onFailure(call: Call<TVDiscoverContainer>, t: Throwable) {
            }
        })
        EspressoIdlingResource.decrement()
        return returnValue
    }

    fun getShowDetails(showId: String): LiveData<ApiResponse<TVShow>>{
        EspressoIdlingResource.increment()
        val returnValue = MutableLiveData<ApiResponse<TVShow>>()
        val call = retrofit.tvMovieRetrofit().create(RetrofitInterfaces::class.java).getShowDetails(showId)
        call.enqueue(object : Callback<TVShow> {
            override fun onResponse(
                call: Call<TVShow>,
                response: Response<TVShow>
            ) {
                returnValue.postValue(ApiResponse.success(response.body()!!))
            }

            override fun onFailure(call: Call<TVShow>, t: Throwable) {
            }
        })
        EspressoIdlingResource.decrement()
        return returnValue
    }

    fun getMovieDetail(showId: String): LiveData<ApiResponse<Movie>>{
        EspressoIdlingResource.increment()
        val returnValue = MutableLiveData<ApiResponse<Movie>>()
        val call = retrofit.tvMovieRetrofit().create(RetrofitInterfaces::class.java).getMovieDetails(showId)
        call.enqueue(object : Callback<Movie> {
            override fun onResponse(
                call: Call<Movie>,
                response: Response<Movie>
            ) {
                returnValue.postValue(ApiResponse.success(response.body()!!))
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
            }
        })
        EspressoIdlingResource.decrement()
        return returnValue
    }
}