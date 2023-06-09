package com.jason.detailmodule.business.fragment

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import coil.load
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jason.commonapplib.common.RouterConstant
import com.jason.detailmodule.R
import com.jason.detailmodule.business.activity.BookDetailViewModel
import com.jason.detailmodule.databinding.BookDetailFragmentBookDescLayoutBinding
import com.jason.detailmodule.http.HttpURL.BASE_IMAGE_URL
import com.jason.mvvm.base.fragment.BaseVmVBFragment


/**
 * @author Jason
 * @description->简介Fragment
 */
class BookDescFragment :
    BaseVmVBFragment<BookDetailViewModel, BookDetailFragmentBookDescLayoutBinding>(),
    OnItemClickListener {

    override fun isDIViewModel() = true

    override fun isShareViewModel() = true

    override fun useLoadSir() = true

    private val dataList = ArrayList<com.jason.homemodule.model.Book>()

    private val mAdapter by lazy {
        BookListAdapter(dataList)
    }

    override fun initView() {
        super.initView()
        mBinding.rvRecommend.adapter = mAdapter
        mAdapter.setOnItemClickListener(this)
    }

    override fun initData() {
        super.initData()
        mViewModel.getAboutList()
    }

    override fun initObserve() {
        mViewModel.playListLiveData.observe(this, Observer {
            mBinding.tvBookDesc.text = it.synopsis
        })

        mViewModel.bookListLiveData.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                dataList.clear()
                dataList.addAll(it)
                mAdapter.notifyDataSetChanged()
            }
        })

    }

    override fun loadNet() {
        super.loadNet()
        mViewModel.getPlayList()
        mViewModel.getAboutList()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val book = dataList[position]
        ARouter.getInstance().build(RouterConstant.DETAIL.DETAIL_MODULE_DETAIL_ROUTE)
            .withString(RouterConstant.KEYS.INTENT_DATA, book.book_id)
            .withString(RouterConstant.KEYS.INTENT_NAME, book.name)
            .navigation()
    }

}


class BookListAdapter(dataList: MutableList<com.jason.homemodule.model.Book>) :
    BaseQuickAdapter<com.jason.homemodule.model.Book, BaseViewHolder>(
        data = dataList,
        layoutResId = R.layout.book_detail_item_book_layout
    ) {

    override fun convert(holder: BaseViewHolder, item: com.jason.homemodule.model.Book) {
        holder.getView<ImageView>(R.id.iv_cover_home_vertical)
            .load(BASE_IMAGE_URL + item.pic)
        holder.setText(R.id.tv_title_home_vertical, item.name)
        holder.setText(R.id.tv_desc_home_vertical, item.synopsis)
        holder.setText(R.id.tv_voice_home_vertical, item.teller)
        holder.setText(R.id.tv_type_home_vertical, item.type)
        holder.setText(R.id.tv_status_home_vertical, item.status + item.count)
    }
}

