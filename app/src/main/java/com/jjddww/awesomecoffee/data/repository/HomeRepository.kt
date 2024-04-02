package com.jjddww.awesomecoffee.data.repository

import android.util.Log
import com.jjddww.awesomecoffee.MemberInfo
import com.jjddww.awesomecoffee.data.api.ApiServiceHelper
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

const val tag = "HomeRepository"

class HomeRepository (private val apiHelper: ApiServiceHelper){
    fun getAdvertisementData() =
        apiHelper.getAdvertisementList()
            .catch {e ->
                Log.e("get Ads Api Error: ", e.toString())
            }

    fun getRecommendMenuData() =
        apiHelper.getRecommendedMenuList()
            .catch { e ->
                Log.e("get Recommended Menu Api Error:", e.toString())
            }

    fun getNewMenuData() = apiHelper.getNewMenuList()
        .catch { e ->
            Log.e("get New Menu Api Error", e.toString())
        }

     fun getStampCount(id: String) =
        apiHelper.getStampCount(id)
            .catch { e ->
                Log.e("get Stamp count Api Error", e.toString())
            }

    private fun sendMemberInfo(user: User?) = apiHelper.sendMemberId(user?.id.toString())


    fun getMemberInfo(){
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(tag, "사용자 정보 요청 실패 $error")
            } else if (user != null) {
                Log.e(tag, "사용자 정보 요청 성공 : $user")
                MemberInfo.memberId = user?.id
                sendMemberInfo(user)
            }

        }

    }
}