package com.submission.movieandtvshow.viewmodelproviders

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.submission.movieandtvshow.dataobjects.Movie
import com.submission.movieandtvshow.dataobjects.TVShow
import com.submission.movieandtvshow.dataobjects.repository.RemoteRepository
import com.submission.movieandtvshow.vo.Resource

class MainViewModel(RemoteRepository: RemoteRepository) : ViewModel()  {
    private val repository = RemoteRepository

    fun getMovies(): LiveData<Resource<PagedList<Movie>>> {
        return repository.getMovies()
    }

    fun getShows(): LiveData<Resource<PagedList<TVShow>>> {
        return repository.getShows()
    }

    fun getFavouriteMovies(): LiveData<PagedList<Movie>>{
        return repository.getFavouriteMovies()
    }

    fun getFavouriteShows(): LiveData<PagedList<TVShow>>{
        return repository.getFavouriteShows()
    }
}