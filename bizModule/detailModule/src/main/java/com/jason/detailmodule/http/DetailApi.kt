package com.jason.detailmodule.http

import com.jason.detailmodule.model.BookDetailModel
import com.jason.detailmodule.model.RecommendBook
import com.jason.mvvm.http.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Jason
 * @description->
 */
interface DetailApi {

    /**
     * 获取详情数据
     */
    @GET("/json/v1/cont/{bookID}.json")
    suspend fun getBookDetail(@Path("bookID") bookID: String): BaseResponse<BookDetailModel>


    /**
     * 获取相关推荐书籍
     */
    @GET("/json/v1/about/{bookID}.json")
    suspend fun getAboutBook(@Path("bookID") bookID: String): BaseResponse<RecommendBook>

}