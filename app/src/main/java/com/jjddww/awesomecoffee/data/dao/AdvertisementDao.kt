package com.jjddww.awesomecoffee.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.jjddww.awesomecoffee.data.Advertisement
import kotlinx.coroutines.flow.Flow

@Dao
interface AdvertisementDao {
    @Query("SELECT url FROM advertisement")
    fun getAdvertisementUrl(): Flow<List<String>>

    @Upsert
    suspend fun upsertAll(advertisements: List<Advertisement>)
}