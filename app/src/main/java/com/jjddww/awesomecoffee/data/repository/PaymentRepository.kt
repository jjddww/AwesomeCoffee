package com.jjddww.awesomecoffee.data.repository

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.jjddww.awesomecoffee.data.dao.CartDao
import com.jjddww.awesomecoffee.data.model.Cart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kr.co.bootpay.android.Bootpay
import kr.co.bootpay.android.events.BootpayEventListener
import kr.co.bootpay.android.models.BootExtra
import kr.co.bootpay.android.models.BootItem
import kr.co.bootpay.android.models.BootUser
import kr.co.bootpay.android.models.Payload

const val BOOTPAY = "boot pay"

class PaymentRepository(private val cartDao: CartDao,
                        private val application: Application) {

    val getCartList: Flow<List<Cart>> = cartDao.getCartList()

    val items: MutableList<BootItem> = ArrayList()

    var isSuccessPayment = MutableStateFlow(false)


    fun clearIsSuccessPayment(){
        isSuccessPayment.value = false
    }

    fun clearItems(){
        if(items.isNotEmpty())
            items.clear()
    }

    fun addItems(name: String, id: String, price: Double, amount: Int){
        items.add(BootItem().setName(name).setId(id).setQty(amount).setPrice(price))
    }

    fun paymentTest(totalPrice: Double, activity: Activity){
        val extra = BootExtra()
            .setCardQuota("0, 2")

        val payload = Payload()

        payload.setApplicationId("6601031b00be04001a06362f")
            .setOrderName("결제 테스트")
            .setPg("이니시스")
            .setMethod("카드")
            .setOrderId("1234")
            .setPrice(totalPrice)
            .setUser(getBootUser())
            .setExtra(extra).items = items


        val map: MutableMap<String, Any> = HashMap()
        map["1"] = "abcdef"
        map["2"] = "abcdef55"
        map["3"] = 1234
        payload.metadata = map

        Bootpay.init(activity, application)
            .setPayload(payload)
            .setEventListener(object : BootpayEventListener {
                override fun onCancel(data: String) {
                    Log.d("$BOOTPAY - onCancel", "cancel: $data")
                    isSuccessPayment.value = false
                }

                override fun onError(data: String) {
                    Log.d("$BOOTPAY - onError", "error: $data")
                    isSuccessPayment.value = false
                }

                override fun onClose() {
                    Log.d("$BOOTPAY - onClose", "closed")
                    Bootpay.removePaymentWindow()
                }

                override fun onIssued(data: String) {
                    Log.d("$BOOTPAY - onIssued", "issued: $data")
                    isSuccessPayment.value = false
                }

                override fun onConfirm(data: String): Boolean {
                    Log.d("$BOOTPAY - onConfirm", "confirm: $data")
                    return true
                }

                override fun onDone(data: String) {
                    Log.d("$BOOTPAY - onDone", data)
                    isSuccessPayment.value = true
                }
            }).requestPayment()
    }


    fun getBootUser(): BootUser? { //TODO 로그인 기능 추가되면 유저 정보 입력
        val userId = "123411aaaaaaaaaaaabd4ss121"
        val user = BootUser()
        user.id = userId
        user.area = "서울"
        user.gender = 1 //1: 남자, 0: 여자
        user.email = "ekdns0817@gmail.com"
        user.phone = "010-1234-4567"
        user.birth = "1988-06-10"
        user.username = "홍길동"
        return user
    }
}