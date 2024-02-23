package com.jjddww.awesomecoffee.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.jjddww.awesomecoffee.data.Advertisement
import kotlinx.coroutines.flow.Flow

@Dao
interface AdvertisementDao {
    @Query("SELECT * FROM advertisement")
    fun getAdvertisement(): Flow<List<Advertisement>>
}