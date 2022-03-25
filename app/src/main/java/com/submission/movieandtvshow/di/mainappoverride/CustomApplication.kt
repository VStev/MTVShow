package com.submission.movieandtvshow.di.mainappoverride

import android.app.Application
import com.submission.movieandtvshow.di.injector.fetcherServiceMod
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CustomApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@CustomApplication)
            //add modules here
            modules(fetcherServiceMod)
        }
    }
}