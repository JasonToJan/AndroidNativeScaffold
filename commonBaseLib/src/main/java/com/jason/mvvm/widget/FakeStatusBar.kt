package com.jason.mvvm.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.BarUtils

/**
 * @author Jason
 * @description->
 */
class FakeStatusBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    init {
        BarUtils.setStatusBarCustom(this)
    }

}