package com.jason.homemodule.business.ui.fragment

import androidx.fragment.app.Fragment
import com.jason.homemodule.R
import com.jason.homemodule.databinding.FragmentHomeLayoutBinding
import com.jason.mvvm.base.fragment.BaseVmVBFragment
import com.jason.mvvm.base.viewmodel.BaseViewModel
import com.jason.mvvm.common.SimpleFragmentPagerAdapter

/**
 * @author Jason
 * @description->
 */
class HomeFragment : BaseVmVBFragment<HomeViewModel, FragmentHomeLayoutBinding>() {

    private val fragmentList: ArrayList<Fragment> = ArrayList<Fragment>()

    override fun isDIViewModel() = true

    override fun initView() {
        mTopBar?.setTitle("首页")
        val stringArray = resources.getStringArray(R.array.tabList)
        fragmentList.add(HomeItemFragment.getInstance(1))
        fragmentList.add(HomeItemFragment.getInstance(2))
        fragmentList.add(HomeItemFragment.getInstance(3))
        fragmentList.add(HomeItemFragment.getInstance(4))
        mBinding.vpHome.offscreenPageLimit = 4
        val simpleFragmentPagerAdapter =
            SimpleFragmentPagerAdapter(childFragmentManager, fragmentList)
        mBinding.vpHome.adapter = simpleFragmentPagerAdapter
        mBinding.tabHome.setViewPager(mBinding.vpHome, stringArray)
    }


    override fun initObserve() {

    }

}


class HomeViewModel : BaseViewModel() {

}
