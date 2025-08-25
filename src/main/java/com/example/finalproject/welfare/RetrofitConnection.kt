package com.example.finalproject.welfare

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit
import kotlin.jvm.java

class RetrofitConnection{

    //객체를 하나만 생성하는 싱글턴 패턴을 적용합니다.
    companion object {
        //API 서버의 주소가 BASE_URL이 됩니다.
        private const val BASE_URL = "https://apis.data.go.kr/B554287/NationalWelfareInformationsV001/"

        var xmlNetworkService : WelfareService
        var parser = TikXml.Builder().exceptionOnUnreadXml(false).build()

        val xmlRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(TikXmlConverterFactory.create(parser))
                .build()

        init{
            xmlNetworkService = xmlRetrofit.create(WelfareService::class.java)
        }
    }
}