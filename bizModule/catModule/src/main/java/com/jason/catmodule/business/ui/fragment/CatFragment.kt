package com.jason.catmodule.business.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.oushangfeng.pinnedsectionitemdecoration.PinnedHeaderItemDecoration
import com.jason.catmodule.R
import com.jason.catmodule.business.adapter.BookCatAdapter
import com.jason.catmodule.databinding.FragmentCatBinding
import com.jason.catmodule.http.CatApi
import com.jason.catmodule.model.BookType
import com.jason.commonapplib.common.RouterConstant
import com.jason.mvvm.base.fragment.BaseVmVBFragment
import com.jason.mvvm.base.viewmodel.BaseViewModel

class CatFragment : BaseVmVBFragment<CatViewModel, FragmentCatBinding>(),
    SwipeRefreshLayout.OnRefreshListener {

    private var dataList = arrayListOf<BookType>()

    private var mAdapter: BookCatAdapter? = null

    override fun isDIViewModel() = true

    override fun useLoadSir() = true

    override fun initView() {
        super.initView()
        mTopBar?.setTitle("分类")
        mBinding.refreshLayout.setColorSchemeColors(resources.getColor(R.color.colorAccent))
        mBinding.refreshLayout.setOnRefreshListener(this)
        mAdapter = BookCatAdapter(dataList).apply {
            setGridSpanSizeLookup { gridLayoutManager, viewType, position ->
                val item = dataList[position]
                when (item.itemType) {
                    BookCatAdapter.CAT_HEADER -> 4
                    BookCatAdapter.CAT_CONTENT -> 1
                    else -> 4
                }
            }

            setOnItemClickListener { _, _, position ->
                ARouter.getInstance().build(RouterConstant.CATEGORY.CAT_MODULE_BOOK_TYPE_LIST_ROUTE)
                    .withString(RouterConstant.KEYS.INTENT_DATA, dataList[position].type_id)
                    .withString(RouterConstant.KEYS.INTENT_NAME, dataList[position].name)
                    .navigation()
            }

        }
        mBinding.rvCat.adapter = mAdapter
        mBinding.rvCat.addItemDecoration(
            PinnedHeaderItemDecoration.Builder(BookCatAdapter.CAT_HEADER)
                .disableHeaderClick(true)
                .create()
        )
    }

    override fun initData() {
        super.initData()
        loadNet()
    }

    override fun initObserve() {

    }

    override fun loadNet() {
        super.loadNet()
        mViewModel.getCatList().observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                dataList.clear()
                dataList.addAll(it)
                mAdapter?.notifyDataSetChanged()
            }
        })
    }

    override fun hideRefresh() {
        super.hideRefresh()
        mBinding.refreshLayout.isRefreshing = false
    }

    override fun onRefresh() {
        loadNet()
    }

}

class CatViewModel(private val api: CatApi) : BaseViewModel() {

    fun getCatList(): LiveData<List<BookType>> = emit {
        val resultData = api.getCategory().resultData()
        val dataList = ArrayList<BookType>()
        resultData.forEach { item ->
            val bookType = BookType(name = item.title, type_id = "")
            bookType.itemType = 1
            dataList.add(bookType)
            dataList.addAll(item.types)
        }
        dataList
    }


}
