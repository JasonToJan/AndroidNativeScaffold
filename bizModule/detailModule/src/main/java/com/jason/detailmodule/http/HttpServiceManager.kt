package com.jason.detailmodule.http

import com.jason.mvvm.http.RetrofitClient


/**
 * @author Jason
 * @description->详情API
 */
object DetailServiceApi :
    DetailApi by RetrofitClient.getRetrofitByUrl(HttpURL.BASE_HOME_URL)
        .create(DetailApi::class.java)