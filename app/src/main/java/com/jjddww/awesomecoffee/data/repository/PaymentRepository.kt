package com.jjddww.awesomecoffee.data.repository

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.jjddww.awesomecoffee.MemberInfo
import com.jjddww.awesomecoffee.data.api.ApiServiceHelper
import com.jjddww.awesomecoffee.data.dao.CartDao
import com.jjddww.awesomecoffee.data.model.Cart
import com.jjddww.awesomecoffee.data.model.Coupon
import com.jjddww.awesomecoffee.utilities.EXTRA_SHOT_PRICE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.co.bootpay.android.Bootpay
import kr.co.bootpay.android.events.BootpayEventListener
import kr.co.bootpay.android.models.BootExtra
import kr.co.bootpay.android.models.BootItem
import kr.co.bootpay.android.models.BootUser
import kr.co.bootpay.android.models.Payload

const val BOOTPAY = "boot pay"

class PaymentRepository(private val fromCart: Boolean,
                        private val cartDao: CartDao,
                        private val application: Application,
                        private val apiHelper: ApiServiceHelper) {

    val getCartList: Flow<List<Cart>> = cartDao.findCheckedItem()

    val items: MutableList<BootItem> = ArrayList()

    var couponId = mutableStateOf(0)

    var isSuccessPayment = MutableStateFlow(false)

    fun settingCouponId(id: Int){
        couponId.value = id
    }

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

                    if(couponId.value != 0) //쿠폰 적용 시 쿠폰 사용, 스탬프 적립x
                        useCoupon()
                    else
                        updateStamp() //쿠폰 사용안할 경우 스탬프 적립o

                    if(fromCart){
                        CoroutineScope(Dispatchers.IO).launch {
                            cartDao.deleteCheckedCartItems()
                        }
                    }
                }
            }).requestPayment()
    }


    fun getBootUser(): BootUser? { //TODO 로그인 기능 추가되면 유저 정보 입력
        val user = BootUser()
        user.id = MemberInfo.memberId.toString()
        user.area = "서울"
        user.gender = 1 //1: 남자, 0: 여자
        user.email = "ekdns0817@gmail.com"
        user.phone = "010-1234-4567"
        user.birth = "1988-06-10"
        user.username = "홍길동"
        return user
    }

    fun updateStamp(){
        apiHelper.updateStamp(MemberInfo.memberId?: 0L, items.size)
    }

    fun useCoupon(){
        if(couponId.value != 0)
            apiHelper.deleteUsedCoupon(couponId.value, MemberInfo.memberId?: 0L)
    }


    fun getCouponList(): Flow<List<Coupon>> =
        apiHelper.getCouponList()
            .catch { e ->
                Log.e("get coupon Api Error", e.toString())
            }
}