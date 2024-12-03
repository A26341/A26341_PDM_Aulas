package com.example.a26341_mynewsapp.models

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
data class CatDataCache (
    @PrimaryKey
    val url: String,
    val catJsonString: String
)

@Dao
interface CatDataCacheDao {
    @Query("SELECT * FROM CatDataCache")
    suspend fun getAll(): List<CatDataCache>

    @Query("SELECT * FROM CatDataCache WHERE url = :url")
    suspend fun getByUrl(url: String): CatDataCache?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(catDataCache: CatDataCache)

    @Query("DELETE FROM CatDataCache WHERE url = :url")
    suspend fun deleteByUrl(url: String)


}