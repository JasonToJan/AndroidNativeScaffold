package com.jason.catmodule.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.jason.catmodule.business.ui.fragment.CatFragment
import com.jason.commonapplib.common.ServiceConstant
import com.jason.commonapplib.provider.ICatProvider

/**
 * @author Jason
 * @description->
 */
@Route(path = ServiceConstant.CAT_MODULE_CAT_ROUTE, name = "分类服务")
class CatProvider : ICatProvider {

    override fun getCatFragment() = CatFragment()

    override fun init(context: Context?) {

    }
}