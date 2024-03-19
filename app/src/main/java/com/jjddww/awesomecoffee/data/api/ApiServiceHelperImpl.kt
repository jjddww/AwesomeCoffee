package com.jjddww.awesomecoffee.data.api

import com.jjddww.awesomecoffee.data.model.Coupon
import com.jjddww.awesomecoffee.data.model.Menu
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApiServiceHelperImpl(private val apiService: ApiService): ApiServiceHelper {
    override fun getAdvertisementList() = flow{
        emit(apiService.getAdvertisementList())
    }

    override fun getRecommendedMenuList(): Flow<List<Menu>>  = flow{
        emit(apiService.getRecommendMenuList())
    }

    override fun getNewMenuList(): Flow<List<Menu>> = flow {
        emit(apiService.getNewMenuList())
    }

    override fun getMenuDescription(id: Int): Flow<List<Menu>> = flow{
        emit(apiService.getMenuDescription(id))
    }

    override fun getMenuList(): Flow<List<Menu>>  = flow {
        emit(apiService.getMenuList())
    }

    override fun getCouponList(): Flow<List<Coupon>> = flow{
        emit(apiService.getCouponList())
    }

    override fun getSearchResult(keyword: String): Flow<List<Menu>> = flow{
        emit(apiService.getMenuSearchResult(keyword))
    }

//    override fun addMenuToCart(
//        menuId: Int,
//        option: String,
//        amount: Int,
//        extraShot: Boolean
//    ): Flow<String> = flow{
//        emit(apiService.addCart(menuId, option, amount, extraShot))
//    }
//
//    override fun removeMenu(id: Int): Flow<String> = flow{
//        emit(apiService.removeCart(id))
//    }
//
//    override fun getCartList(): Flow<List<CartItem>> = flow{
//        emit(apiService.getCartList())
//    }

}