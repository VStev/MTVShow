package com.submission.movieandtvshow.di.injector

import com.submission.movieandtvshow.dataobjects.repository.LocalDataSource
import com.submission.movieandtvshow.dataobjects.repository.RemoteRepository
import com.submission.movieandtvshow.dataobjects.room.EntertainmentDatabase
import com.submission.movieandtvshow.utilities.AppExecutors
import com.submission.movieandtvshow.viewmodelproviders.MainViewModel
import com.submission.movieandtvshow.viewmodelproviders.ShowDetailsViewModel
import com.submission.movieandtvshow.webapi.RetrofitCallback
import com.submission.movieandtvshow.webapi.RetrofitGenerator
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@JvmField
val fetcherServiceMod = module {
    viewModel {
        ShowDetailsViewModel(
            RemoteRepository.getInstance(
                RetrofitCallback.getInstance(RetrofitGenerator()),
                LocalDataSource.getInstance(
                    EntertainmentDatabase.getInstance(androidContext()).entertainmentDao()
                ),
                AppExecutors()
            )
        )
    }
    viewModel {
        MainViewModel(
            RemoteRepository.getInstance(
                RetrofitCallback.getInstance(RetrofitGenerator()),
                LocalDataSource.getInstance(
                    EntertainmentDatabase.getInstance(androidContext()).entertainmentDao()
                ),
                AppExecutors()
            )
        )
    }
}