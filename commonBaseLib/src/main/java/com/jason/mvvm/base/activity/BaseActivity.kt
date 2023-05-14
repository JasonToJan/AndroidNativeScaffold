package com.jason.mvvm.base.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import androidx.savedstate.ViewTreeSavedStateRegistryOwner
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ToastUtils
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.qmuiteam.qmui.widget.QMUITopBar
import com.jason.mvvm.base.IView
import com.jason.mvvm.base.view.BaseViewComponent
import com.jason.mvvm.loadsir.*
import com.jason.mvvm.widget.LoadDialog
import com.jason.mvvm.R
import me.jessyan.autosize.AutoSize
import org.greenrobot.eventbus.EventBus

/**
 * @author Jason
 * Description->没有使用DataBinding的普通基类
 */
abstract class BaseActivity : AppCompatActivity(), IView {

    abstract fun getLayoutId(): Int
    private var mLoadService: LoadService<Any>? = null
    private lateinit var loadingDialog: LoadDialog
    open var mTopBar: QMUITopBar? = null
    open var mContext: Activity? = null

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        AutoSize.autoConvertDensityOfGlobal(this)
        return super.onCreateView(name, context, attrs)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        BarUtils.transparentStatusBar(this)
        super.onCreate(savedInstanceState)
        initViewTreeOwners()
        setContentLayout()
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        mContext = this
        initTopBar()
        initLoadSir()
        initViewData()
    }

    open fun setContentLayout() {
        setContentView(getLayoutId())
    }

    /**
     * 为了给自定义的ViewComponent内部使用，能正常使用lifecycleOwner和viewModelScope----liveData+ViewModel的模式开发
     */
    fun initViewTreeOwners() {
        // Set the view tree owners before setting the content view so that the inflation process
        // and attach listeners will see them already present
        ViewTreeLifecycleOwner.set(window.decorView, this)
        ViewTreeViewModelStoreOwner.set(window.decorView, this)
        ViewTreeSavedStateRegistryOwner.set(window.decorView, this)
    }


    private fun initTopBar() {
        mTopBar = findViewById(R.id.qm_topbar)
        val fakeStatusBar = findViewById<View>(R.id.fake_status_bar)
        fakeStatusBar?.run {
            BarUtils.setStatusBarCustom(this)
        }
        mTopBar?.run {
            if (isShowBack()) {
                addLeftBackImageButton().setOnClickListener {
                    finish()
                }
            }
        }
    }

    open fun setTitleName(title: String?) {
        title?.run {
            mTopBar?.setTitle(title)
        }
    }

    /**
     * 是否展示返回按钮
     */
    open fun isShowBack(): Boolean = true

    protected open fun useEventBus(): Boolean = false

    open fun useShimmerLayout(): Boolean = false

    /**
     * 骨架屏布局文件
     */
    open fun shimmerLayout() = R.layout.layout_default_item_shimmer_layout

    /**
     * 骨架屏是否是列表骨架屏，此时的shimmerLayout代表Rv的item布局，如果不是列表，就是普通占位的layout
     */
    open fun shimmerList(): Boolean = true

    /**
     * 列表类骨架屏长度
     */
    open fun shimmerListSize(): Int = 8

    open fun initViewData() {

    }

    open fun initLoadSir() {
        if (getLayoutId() > 0) {
            val content = findViewById<View>(R.id.view_content_loadsir)
            content?.let {
                setLoadSir(it)
            }
        }
    }

    protected fun setLoadSir(it: View) {
        mLoadService = LoadSir.getDefault().register(it) {
            mLoadService?.showCallback(LoadingCallback::class.java)
            loadNet()
        }
        if (useShimmerLayout()) {
            mLoadService?.loadLayout?.addCallback(ShimmerCallback(shimmerLayout(), shimmerList(), shimmerListSize()))
            mLoadService?.showCallback(ShimmerCallback::class.java)
        }

    }

    private fun initViewComponent(it: View) {
        it?.run {
            if (this is ViewGroup) {
                var vp: ViewGroup = it as ViewGroup
                (0..vp.childCount).forEachIndexed { index, item ->
                    val childAt = this.getChildAt(index)
                    if (childAt is ViewGroup) {
                        if (childAt is BaseViewComponent<*>) {
                            val baseViewComponent = childAt as BaseViewComponent<*>
                            baseViewComponent.init()
                        } else {
                            initViewComponent(childAt)
                        }
                    }
                }
            }
        }
    }

    open fun loadNet() {

    }

    fun showError() {
        hideRefresh()
        mLoadService?.showCallback(ErrorCallback::class.java)
    }

    fun showSuccess() {
        hideRefresh()
        mLoadService?.showSuccess()
    }

    fun showEmpty() {
        hideRefresh()
        mLoadService?.showCallback(EmptyCallback::class.java)
    }

    fun showLoading() {
        hideRefresh()
        mLoadService?.showCallback(LoadingCallback::class.java)
    }

    fun showTimeOut() {
        hideRefresh()
        mLoadService?.showCallback(TimeoutCallback::class.java)
    }

    fun showToast(toastMsg: String) {
        ToastUtils.showShort(toastMsg)
    }

    fun showDialogLoading(loadingString: String? = "") {
        if (!this::loadingDialog.isInitialized) {
            mContext?.run {
                loadingDialog = LoadDialog(this)
            }
        }
        this.loadingDialog.showLoading(loadingString)
    }

    fun hideLoading() {
        if (this::loadingDialog.isInitialized) {
            loadingDialog.hideLoading()
        }
    }

    override fun onDestroy() {
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
    }

    override fun hideRefresh() {
        super.hideRefresh()
    }


}