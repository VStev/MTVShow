package com.submission.movieandtvshow.viewmodelproviders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.submission.movieandtvshow.dataobjects.Movie
import com.submission.movieandtvshow.dataobjects.TVShow
import com.submission.movieandtvshow.dataobjects.repository.RemoteRepository
import com.submission.movieandtvshow.vo.Resource

class ShowDetailsViewModel(RemoteRepository: RemoteRepository) : ViewModel() {
    private var showID : String = ""
    private val repository = RemoteRepository

    fun setShowID(showID: String?){
        if (showID != null) {
            this.showID = showID
        }
    }

    fun getMovie(): LiveData<Resource<Movie>> {
        return repository.getMovieDetail(showID)
    }

    fun getShow(): LiveData<Resource<TVShow>>  {
        return repository.getShowDetails(showID)
    }

    fun setFav(argument: Int, state: Boolean) {
        when (argument){
            1 -> {
                repository.setFavouriteShow(showID, state)
            }
            2 -> {
                repository.setFavouriteMovie(showID, state)
            }
        }
    }
}