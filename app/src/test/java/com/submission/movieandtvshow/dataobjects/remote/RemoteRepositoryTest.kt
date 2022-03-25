package com.submission.movieandtvshow.dataobjects.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.submission.movieandtvshow.dataobjects.Movie
import com.submission.movieandtvshow.dataobjects.TVShow
import com.submission.movieandtvshow.dataobjects.remote.dataentities.MovieDiscoverContainer
import com.submission.movieandtvshow.dataobjects.remote.dataentities.TVDiscoverContainer
import com.submission.movieandtvshow.dataobjects.repository.LocalDataSource
import com.submission.movieandtvshow.dataobjects.repository.RemoteRepository
import com.submission.movieandtvshow.utilities.AppExecutors
import com.submission.movieandtvshow.utilities.JsonFilesInKt
import com.submission.movieandtvshow.utilities.LiveDataUtility
import com.submission.movieandtvshow.utilities.PagedListUtility
import com.submission.movieandtvshow.vo.Resource
import com.submission.movieandtvshow.webapi.RetrofitCallback
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executor


@RunWith(MockitoJUnitRunner.Silent::class)
class RemoteRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: RemoteRepository

    @Mock
    private lateinit var retrofitCallback: RetrofitCallback

    @Mock
    private lateinit var localDataSource: LocalDataSource

    private val executor = Executor { it.run()}
    private val appExecutors = AppExecutors(executor, executor, executor)

    @Before
    fun setUp() {
        repository = RemoteRepository(retrofitCallback, localDataSource, appExecutors)
    }

    @Test
    fun getMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Movie>
        val movieLists: List<Movie> = Gson().fromJson(JsonFilesInKt.discoverMovie, MovieDiscoverContainer::class.java).result
        val result = Resource.success(PagedListUtility.mockPagedList(Gson().fromJson(JsonFilesInKt.discoverMovie, MovieDiscoverContainer::class.java).result))
        `when`(localDataSource.getMovies()).thenReturn(dataSourceFactory)
        repository.getMovies()
        verify(localDataSource).getMovies()
        assertNotNull(result)
        assertEquals(movieLists.size, result.data?.size)
    }

    @Test
    fun getShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TVShow>
        val showLists: List<TVShow> = Gson().fromJson(JsonFilesInKt.discoverShow, TVDiscoverContainer::class.java).result
        val result =  Resource.success(PagedListUtility.mockPagedList(Gson().fromJson(JsonFilesInKt.discoverShow, TVDiscoverContainer::class.java).result))
        `when`(localDataSource.getShows()).thenReturn(dataSourceFactory)
        repository.getShows()
        verify(localDataSource).getShows()
        assertNotNull(result)
        assertEquals(showLists.size, result.data?.size)
    }

    @Test
    fun getShowDetails() {
        val showPreset: TVShow = Gson().fromJson(JsonFilesInKt.showPreset, TVShow::class.java)
        val result =  MutableLiveData<TVShow>()
        result.value = showPreset
        `when`(localDataSource.getShowDetails(eq("78204"))).thenReturn(result)
        val shows = LiveDataUtility.getValue(repository.getShowDetails(eq("78204")))
        verify(localDataSource).getShowDetails(eq("78204"))
        assertNotNull(shows)
        assertEquals("78204", shows.data?.showID)
    }

    @Test
    fun getMovieDetail() {
        val moviePreset: Movie = Gson().fromJson(JsonFilesInKt.moviePreset, Movie::class.java)
        val result =  MutableLiveData<Movie>()
        result.value = moviePreset
        `when`(localDataSource.getMovieDetails(eq("458576"))).thenReturn(result)
        val shows = LiveDataUtility.getValue(repository.getMovieDetail(eq("458576")))
        verify(localDataSource).getMovieDetails(eq("458576"))
        assertNotNull(shows)
        assertEquals("458576", shows.data?.movieID)
    }

    @Test
    fun setFavouriteMovie() {
        localDataSource.setFavouriteMovie("458576", true)
        verify(localDataSource, times(1)).setFavouriteMovie("458576", true)
    }

    @Test
    fun setFavouriteShow(){
        localDataSource.setFavouriteShow("78204", true)
        verify(localDataSource, times(1)).setFavouriteShow("78204", true)
    }
}