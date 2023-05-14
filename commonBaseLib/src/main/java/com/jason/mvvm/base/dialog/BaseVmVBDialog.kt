package com.jason.mvvm.base.dialog

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.jason.mvvm.base.viewmodel.BaseViewModel
import com.jason.mvvm.enums.ViewStatusEnum
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

/**
 * @author Jason
 * @description->Viewmodel+ViewBinding请求的Dialog
 */
abstract class BaseVmVBDialog<VM : BaseViewModel, VB : ViewBinding> : BaseVBDialog<VB>() {

    protected val mViewModel: VM by lazy {
        if (isDIViewModel()) {
            val clazz =
                this.javaClass.kotlin.supertypes[0].arguments[0].type!!.classifier!! as KClass<VM>
            getViewModel<VM>(clazz = clazz)
        } else {
            val types = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
            ViewModelProvider(this).get<VM>(types[0] as Class<VM>)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(mViewModel)
        initView()
        initViewModel()
        initStateObserve()
        initObserve()
        if (savedInstanceState == null) {
            initData()
        }
    }

    /**
     * 初始化自己的观察者
     */
    abstract fun initObserve()

    /**
     * 普通加载数据
     */
    open fun initData() {
        // Override if need
    }

    /**
     * 如果有需要初始化View数据
     */
    open fun initView() {
        // Override if need
    }

    /**
     * 是否采用依赖注入的方式注入ViewModel
     */
    open fun isDIViewModel(): Boolean = false

    open fun initStateObserve() {
        mViewModel.viewStatus.observe(viewLifecycleOwner, Observer {
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


    private fun initViewModel() {
        //通过lazy初始化
//        mViewModel = ViewModelProvider(this).get(viewModelClass())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        lifecycle.removeObserver(mViewModel)
    }

}