package com.jason.catmodule.business.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jason.catmodule.R
import com.jason.catmodule.model.BookType

/**
 * @author Jason
 * @description->
 */
class BookCatAdapter(data: MutableList<BookType>) :
    BaseMultiItemQuickAdapter<BookType, BaseViewHolder>(data = data) {

    companion object {
        const val CAT_CONTENT = 0
        const val CAT_HEADER = 1
    }

    init {
        addItemType(CAT_HEADER, R.layout.cat_module_item_cat_section_layout)
        addItemType(CAT_CONTENT, R.layout.cat_module_item_cat_content_layout)
    }


    override fun convert(holder: BaseViewHolder, item: BookType) {
        when (item.itemType) {
            CAT_CONTENT -> {
                holder.setText(R.id.qmbt_content_title, item.name)
            }

            CAT_HEADER -> {
                holder.setText(R.id.tv_title_home_section, item.name)
            }

        }
    }
}