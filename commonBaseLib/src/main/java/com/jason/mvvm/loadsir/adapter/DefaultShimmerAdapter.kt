package com.jason.mvvm.loadsir.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jason.mvvm.R

/**
 * @author Jason
 * @description:
 **/
class DefaultShimmerAdapter(layoutRes: Int? = R.layout.layout_default_item_shimmer_layout) :
    BaseQuickAdapter<Int, BaseViewHolder>(layoutResId = layoutRes ?: R.layout.layout_default_item_shimmer_layout) {
    override fun convert(holder: BaseViewHolder, item: Int) {
    }
}