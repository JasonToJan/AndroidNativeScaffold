package com.jason.homemodule.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.jason.commonapplib.common.ServiceConstant
import com.jason.commonapplib.provider.IHomeProvider
import com.jason.homemodule.business.ui.fragment.HomeFragment
import com.jason.homemodule.business.ui.fragment.TestFragment

/**
 * @author Jason
 * @description->
 */
@Route(path = ServiceConstant.HOME_MODULE_HOME_ROUTE, name = "首页服务")
class HomeProvider : IHomeProvider {

    override fun getHomeFragment() =
        HomeFragment()

    override fun getTestFragment() =
        TestFragment()

    override fun init(context: Context?) {

    }
}