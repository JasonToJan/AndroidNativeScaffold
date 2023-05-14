package com.jason.homemodule.http

import com.jason.homemodule.model.HomeListModel
import com.jason.mvvm.http.BaseResponse
import retrofit2.http.GET

/**
 * @author Jason
 * @description->
 */
interface HomeApi {

    /**
     * 最新更新
     */
    @GET("/json/v1/home/update.json")
    suspend fun getHomeUpdate(): BaseResponse<HomeListModel>


    /**
     * 最新更新
     */
    @GET("/json/v1/home/serial.json")
    suspend fun getHomeSeries(): BaseResponse<HomeListModel>


    /**
     * 热门评书
     */
    @GET("/json/v1/home/ping.json")
    suspend fun getHomeHotBook(): BaseResponse<HomeListModel>


    /**
     * 热门有声
     */
    @GET("/json/v1/home/story.json")
    suspend fun getHomeHotVoice(): BaseResponse<HomeListModel>

}