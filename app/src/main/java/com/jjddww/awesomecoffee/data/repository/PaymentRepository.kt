package com.jjddww.awesomecoffee.data.repository

import android.app.Activity
import android.app.Application
import android.util.Log
import com.jjddww.awesomecoffee.data.dao.CartDao
import com.jjddww.awesomecoffee.data.model.Cart
import kotlinx.coroutines.flow.Flow
import kr.co.bootpay.android.Bootpay
import kr.co.bootpay.android.events.BootpayEventListener
import kr.co.bootpay.android.models.BootExtra
import kr.co.bootpay.android.models.BootItem
import kr.co.bootpay.android.models.BootUser
import kr.co.bootpay.android.models.Payload

class PaymentRepository(private val cartDao: CartDao, val application: Application) {

    val getCartList: Flow<List<Cart>> = cartDao.getCartList()

    val items: MutableList<BootItem> = ArrayList()

    fun addItems(name: String, id: String, price: Double){
        items.add(BootItem().setName(name).setId(id).setQty(1).setPrice(price))
    }

    fun paymentTest(totalPrice: Double){
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
        //        payload.setMetadata(new Gson().toJson(map));
        Bootpay.init(, application)
            .setPayload(payload)
            .setEventListener(object : BootpayEventListener {
                override fun onCancel(data: String) {
                    Log.d("bootpay", "cancel: $data")
                }

                override fun onError(data: String) {
                    Log.d("bootpay", "error: $data")
                }

                override fun onClose() {
                    Bootpay.removePaymentWindow()
                }

                override fun onIssued(data: String) {
                    Log.d("bootpay", "issued: $data")
                }

                override fun onConfirm(data: String): Boolean {
                    Log.d("bootpay", "confirm: $data")
                    //                        Bootpay.transactionConfirm(data); //재고가 있어서 결제를 진행하려 할때 true (방법 1)
                    return true //재고가 있어서 결제를 진행하려 할때 true (방법 2)
                    //                        return false; //결제를 진행하지 않을때 false
                }

                override fun onDone(data: String) {
                    Log.d("done", data)
                }
            }).requestPayment()
    }


    fun getBootUser(): BootUser? {
        val userId = "123411aaaaaaaaaaaabd4ss121"
        val user = BootUser()
        user.id = userId
        user.area = "서울"
        user.gender = 1 //1: 남자, 0: 여자
        user.email = "test1234@gmail.com"
        user.phone = "010-1234-4567"
        user.birth = "1988-06-10"
        user.username = "홍길동"
        return user
    }
}