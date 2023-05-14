package com.jason.homemodule

import com.blankj.utilcode.util.FragmentUtils
import com.jason.homemodule.business.ui.fragment.HomeFragment
import com.jason.homemodule.databinding.ActivityMainBinding
import com.jason.mvvm.base.activity.BaseVBActivity

class MainActivity : BaseVBActivity<ActivityMainBinding>() {

    override fun initViewData() {
        super.initViewData()
        val homeFragment = HomeFragment()
        FragmentUtils.add(supportFragmentManager, homeFragment, R.id.frame_home_module)
        FragmentUtils.show(homeFragment)
    }

}