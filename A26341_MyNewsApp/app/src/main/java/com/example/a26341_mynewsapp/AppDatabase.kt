package com.example.a26341_mynewsapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a26341_mynewsapp.models.CatDataCache
import com.example.a26341_mynewsapp.models.CatDataCacheDao

@Database (entities = [CatDataCache::class], version = 1)

abstract class AppDatabase : RoomDatabase() {

    abstract fun catDataCacheDao(): CatDataCacheDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase?=null

        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class::java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "db_cats"
                        ).fallbackToDestructiveMigration()
                            .build()
                    }
                }

            }
            return INSTANCE
        }
    }
}