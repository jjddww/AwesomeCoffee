package com.jjddww.awesomecoffee.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jjddww.awesomecoffee.MemberInfo
import com.jjddww.awesomecoffee.data.api.ApiServiceHelperImpl
import com.jjddww.awesomecoffee.data.api.RetrofitClient
import com.jjddww.awesomecoffee.data.model.BannerAd
import com.jjddww.awesomecoffee.data.model.Menu
import com.jjddww.awesomecoffee.data.repository.HomeRepository
import com.kakao.sdk.user.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date


class HomeViewModel: ViewModel() {

    private val repository = HomeRepository(ApiServiceHelperImpl(RetrofitClient.retrofit))

    val advertisements: LiveData<List<BannerAd>> = repository.getAdvertisementData().asLiveData()

    val recommendedMenuList: LiveData<List<Menu>> = repository.getRecommendMenuData().asLiveData()

    val newMenuList: LiveData<List<Menu>> = repository.getNewMenuData().asLiveData()

    var stampCount = MutableLiveData(0)

    fun getStampCount(){
        viewModelScope.launch {
            if(MemberInfo.memberId != 0L)
                stampCount.value = repository.getStampCount(MemberInfo.memberId.toString()).first()
        }
    }


    fun settingMemberInfo(){
        viewModelScope.launch {
            repository.getMemberInfo()
        }
    }
}