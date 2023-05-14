package com.jason.commonapplib.provider

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * 自定义Provider
 * 生产目标Fragment
 */
interface ICatProvider : IProvider {

    fun getCatFragment(): Fragment

}