package com.jjddww.awesomecoffee.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jjddww.awesomecoffee.data.AppDatabase
import com.jjddww.awesomecoffee.data.repository.AdvertisementRepository

class AdImageUrlListViewModel (application: Application) : ViewModel(){ //삭제 예정

//        val advertisementUrl : LiveData<List<String>>
//        init {
//            val advertisementDb = AppDatabase.getInstance(application)
//            val advertisementDao = advertisementDb.advertisementDao()
//            val advertisementRepository = AdvertisementRepository(advertisementDao)
//            advertisementUrl = advertisementRepository.getAdvertisementUrl().asLiveData()
//        }

}