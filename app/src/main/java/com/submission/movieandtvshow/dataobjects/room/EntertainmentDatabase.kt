package com.submission.movieandtvshow.dataobjects.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.submission.movieandtvshow.dataobjects.Movie
import com.submission.movieandtvshow.dataobjects.TVShow

@Database(entities = [Movie::class, TVShow::class], version = 1, exportSchema = false)
abstract class EntertainmentDatabase : RoomDatabase() {
    abstract fun entertainmentDao(): EntertainmentDAO

    companion object {

        @Volatile
        private var INSTANCE: EntertainmentDatabase? = null
        fun getInstance(context: Context): EntertainmentDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    EntertainmentDatabase::class.java,
                    "Entertainment.db"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}