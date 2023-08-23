package com.walton.waltonicc.api

import com.walton.waltonicc.models.bannermodels.BannerModel
import com.walton.waltonicc.models.newsmodel.NewsModel
import com.walton.waltonicc.models.schedule.ScheduleModel
import retrofit2.Response
import retrofit2.http.GET

interface ICCWorldCupAPI {

    @GET("/api/get-banner")
    suspend fun getBannerDetails(): Response<BannerModel>

    @GET("/api/get-schedule")
    suspend fun getScheduleData(): Response<ScheduleModel>

    @GET("/api/get-cricket-news")
    suspend fun getNewsData(): Response<NewsModel>
}