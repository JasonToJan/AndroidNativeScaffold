package com.jason.mvvm.base.activity

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jason.mvvm.base.viewmodel.BaseViewModel
import com.jason.mvvm.enums.ViewStatusEnum
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

/**
 * @author Jason
 * @description->使用了DataBinding+Viewmodel基类
 */
abstract class BaseVmDBActivity<VM : BaseViewModel, DB : ViewDataBinding>
    : BaseDBActivity<DB>() {
    protected val mViewModel: VM by lazy {
        if (isDIViewModel()) {
            //koin 注入
            val clazz =
                this.javaClass.kotlin.supertypes[0].arguments[0].type!!.classifier!! as KClass<VM>
            getViewModel<VM>(clazz = clazz)
        } else {
            val types = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
            ViewModelProvider(this).get<VM>(types[0] as Class<VM>)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(mViewModel)
        initDataBindingAndLiveData()
        initView()
        initStatusObserve()
        initObserve()
        // 因为Activity恢复后savedInstanceState不为null，
        // 重新恢复后会自动从ViewModel中的LiveData恢复数据，
        // 不需要重新初始化数据。
        if (savedInstanceState == null) {
            initData()
            loadNet()
        }
    }

    private fun initDataBindingAndLiveData() {
        mDataBind.lifecycleOwner = this
    }

    open fun initView() {

    }

    open fun initData() {

    }

    /**
     * 是否采用依赖注入的方式注入ViewModel
     */
    open fun isDIViewModel(): Boolean = false

    /**
     * 初始化业务观察LiveData
     */
    abstract fun initObserve()

    private fun initStatusObserve() {
        mViewModel.viewStatus.observe(this, Observer {
            when (it) {
                ViewStatusEnum.SUCCESS -> {
                    showSuccess()
                }

                ViewStatusEnum.ERROR -> {
                    showError()
                }

                ViewStatusEnum.EMPTY -> {
                    showEmpty()
                }

                ViewStatusEnum.NETWORKERROR -> {
                    showTimeOut()
                }

            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(mViewModel)
    }


}