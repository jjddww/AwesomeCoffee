# ☕ Awesome Coffee

</br>

개발기간: 2024.02.20 ~ 2024.04.09

</br>

## ⭐️ 개요
</br>

사용자가 메뉴를 탐색하고 주문, 결제할 수 있는 커피 오더 앱.

</br>

<figure>
    <img src="https://github.com/jjddww/AwesomeCoffee/assets/50095740/ac8aaee6-500f-4301-a81a-65ae8cc97dfa", width="360", height="800">
</figure>


</br></br>

## 추가) 실행을 위한 apk 다운로드 </br></br>
Awesome coffee는 구글플레이에 출시된 앱이 아닌 개인 프로젝트이므로 카카오 로그인 기능은 현재 디버그 키 사용 중입니다.</br></br>
디버그 키를 사용할 경우 PC 기기마다 해시키를 구해서 카카오 Developer 프로젝트에 추가시켜야 로그인 API가 동작하므로 </br>
프로젝트를 clone하여 실행시키실 경우 로그인 기능이 동작하지 않을 수 있습니다.</br></br>
Awesome coffee를 실행하실 분들은 apk를 다운로드 해주세요.

[Awesome Coffee apk](https://github.com/jjddww/AwesomeCoffee/blob/main/awesome_coffee.apk)

</br></br>


## 🔨 기술 스택 & 라이브러리

* Jetpack
  - Compose
  - ViewModel
  - LiveData
  - Room
  - Navigation

* Retrofit2 & OkHttp3
* coroutine & coroutine flow
* Coil
* Kakao SDK (로그인 구현)
* BootPay API (결제 연동)

* 아키텍처
  - MVVM (Compose - ViewModel - Model)
  - Repository 패턴

* 서버
  - 구글 GCP (Google Cloud Platform)
  - NodeJs express
  - Mysql

</br></br>

## 💡 기능 소개
</br>

[기능 소개 링크](https://github.com/jjddww/AwesomeCoffee/blob/main/%EA%B8%B0%EB%8A%A5.md)

</br></br>


## 아키텍처 다이어그램

![다이어그램3](https://github.com/jjddww/AwesomeCoffee/assets/50095740/eed8fddd-b8f6-4585-8a21-04bdbcdfd676)
