package com.jason.mvvm.widget

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.jason.mvvm.R
import kotlin.jvm.Throws

/**
 * @author Jason
 * @Description->
 */
class LoadDialog @JvmOverloads constructor(context: Context) {
    private var dialog: Dialog? = null
    private var textView: TextView? = null

    init {
        initDialog(context)
    }

    private fun initDialog(context: Context) {
        dialog = Dialog(context, R.style.Custom_Loading_Style)
        dialog?.setCancelable(true)
        dialog?.show()
        dialog?.setContentView(R.layout.custom_loading_view)
        dialog?.setCanceledOnTouchOutside(false)
        textView = dialog!!.findViewById(R.id.loading_text)
    }

    fun setCanceledOnTouchOutside(canceled: Boolean) {
        dialog!!.setCanceledOnTouchOutside(canceled)
    }

    @Throws(Exception::class)
    fun showLoading(message: String? = "") {
        if (TextUtils.isEmpty(message)) {
            textView?.visibility = View.GONE
        } else {
            textView?.visibility = View.VISIBLE
            textView?.text = message
        }
        if (dialog!!.isShowing) return
        dialog?.show()
    }

    fun hideLoading() {
        if (null != dialog && dialog!!.isShowing) dialog!!.dismiss()
    }

    fun showing(): Boolean {
        return null != dialog && dialog!!.isShowing
    }
}