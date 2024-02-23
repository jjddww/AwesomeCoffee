package com.jjddww.awesomecoffee.data.repository

import com.jjddww.awesomecoffee.data.dao.AdvertisementDao

class AdvertisementRepository (private val advertisementDao: AdvertisementDao){

    fun getAdvertisement() = advertisementDao.getAdvertisement()




    companion object {
        @Volatile private var instance: AdvertisementRepository? = null

        fun getInstance(advertisementDao: AdvertisementDao) =
            instance ?: synchronized(this) {
                instance ?: AdvertisementRepository(advertisementDao).also { instance = it }
            }
    }
}