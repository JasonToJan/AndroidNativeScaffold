package com.jason.catmodule.http

import com.jason.mvvm.http.RetrofitClient



/**
 * @author Jason
 * @description-> 首页API
 */
object CatServiceApi :
    CatApi by RetrofitClient.getRetrofitByUrl(HttpURL.BASE_HOME_URL)
        .create(CatApi::class.java)