package com.jjddww.awesomecoffee.data.repository

import com.jjddww.awesomecoffee.data.dao.AdvertisementDao

class AdvertisementRepository (private val advertisementDao: AdvertisementDao){

    fun getAdvertisementUrl() = advertisementDao.getAdvertisementUrl()

}