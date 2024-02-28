package com.jjddww.awesomecoffee.data.repository

import com.jjddww.awesomecoffee.data.dao.AdvertisementDao

class AdvertisementRepository (private val advertisementDao: AdvertisementDao){ //삭제 예정

    fun getAdvertisementUrl() = advertisementDao.getAdvertisementUrl()

}