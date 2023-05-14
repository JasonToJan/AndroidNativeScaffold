package com.jason.homemodule.http

import com.jason.mvvm.http.RetrofitClient


/**
 * @author Jason
 * @description-> 首页API
 */
object HomeServiceApi :
    HomeApi by RetrofitClient.getRetrofitByUrl(HttpURL.BASE_HOME_URL)
        .create(HomeApi::class.java)