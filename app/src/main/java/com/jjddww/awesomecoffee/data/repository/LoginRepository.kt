package com.jjddww.awesomecoffee.data.repository

import android.app.Activity
import android.app.Application
import android.util.Log
import com.jjddww.awesomecoffee.MemberInfo
import com.jjddww.awesomecoffee.data.api.ApiServiceHelper
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.flow.MutableStateFlow

const val TAG = "LoginRepository"

class LoginRepository(private val apiHelper: ApiServiceHelper) {

    var isSuccessLogin = MutableStateFlow(false)

    // 이메일 로그인 콜백
    private val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "로그인 실패 $error")
        } else if (token != null) {
            Log.e(TAG, "로그인 성공 ${token.accessToken}")
            isSuccessLogin.value = true
        }
    }

    fun LoginKakao(activity: Activity){
        // 카카오톡 설치 확인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(activity)) {
            // 카카오톡 로그인
            UserApiClient.instance.loginWithKakaoTalk(activity) { token, error ->
                // 로그인 실패 부분
                if (error != null) {
                    Log.e(TAG, "로그인 실패 $error")
                    // 사용자가 취소
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled ) {
                        return@loginWithKakaoTalk
                    }
                    // 다른 오류
                    else {
                        UserApiClient.instance.loginWithKakaoAccount(activity, callback = mCallback) // 카카오 이메일 로그인
                    }
                }
                // 로그인 성공 부분
                else if (token != null) {
                    Log.e(TAG, "로그인 성공 ${token.accessToken}")
                    isSuccessLogin.value = true
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(activity, callback = mCallback) // 카카오 이메일 로그인
        }
    }

    fun checkLoginToken() {
        isSuccessLogin.value = false

        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { accessToken, error ->
                if (error != null) {
                    Log.e("로그인 - 로그인 에러", error.toString())
                    if (error is KakaoSdkError && error.isInvalidTokenError()) //로그인 필요
                        isSuccessLogin.value = false
                    else //기타 에러
                        isSuccessLogin.value = false
                } else //토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
                {
                    Log.e("로그인 - 토큰 유효성 체크", accessToken.toString())
                    isSuccessLogin.value = true


                }
            }
        }
        else {
            isSuccessLogin.value = false
            Log.e("로그인 - 토큰 유효성 체크", "토큰 없음.")
        }

    }

    fun requestLogout(){
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            }
            else {
                Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                isSuccessLogin.value = false
                MemberInfo.initialMemberInfo()
            }

        }
    }
}