package com.example.finalproject.welfare

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// https://apis.data.go.kr/B554287/NationalWelfareInformationsV001/
// NationalWelfarelistV001?serviceKey=mrl0VZdVcapf7YIOh4FbXa6CpQD72OGocCDMdbSmhZCEvShglTeEvyG%2B5E%2BH7ZZRhUbSdxzqQ6WVKvvYkH5b3A%3D%3D
// &callTp=L&pageNo=1&numOfRows=10&srchKeyCode=001&lifeArray=007&trgterIndvdlArray=050&intrsThemaArray=010&age=20&onapPsbltYn=Y&orderBy=popular

interface WelfareService {
    @GET("NationalWelfarelistV001")
    fun getWelfareData(
        @Query("serviceKey") serviceKey: String,
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("lifeArray") lifeArray: String,
        @Query("callTp") callTp: String = "L",
        @Query("srchKeyCode") srchKeyCode: String = "001",
        @Query("trgterIndvdlArray") trgter: String? = null,
        @Query("intrsThemaArray") interest: String? = null,
        @Query("age") age: Int = 0,
        @Query("onapPsbltYn") onlineApply: String? = null,
        @Query("orderBy") orderBy: String = "popular"
    ): Call<WelfareResponse>
}
