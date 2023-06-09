package com.jason.detailmodule.di


import com.jason.detailmodule.business.activity.BookDetailViewModel
import com.jason.detailmodule.business.fragment.BookDescFragment
import com.jason.detailmodule.business.fragment.PlayListFragment
import com.jason.detailmodule.http.DetailApi
import com.jason.detailmodule.http.DetailServiceApi
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * 三者的关系是一层一层依赖，中间的查找在koin里面通过get（）查找获取 -viewmodle依赖Respository --> Respository依赖remoteModule的ApiService
 */


//viewmodel注入管理类
val viewModelModule = module {
    viewModel { BookDetailViewModel(get()) }
}

val fragmentModule = module {
    fragment { BookDescFragment() }
    fragment { PlayListFragment() }
}


//远程请求数据类
val remoteModule = module {
    //single 单列注入
    single<DetailApi> { DetailServiceApi }
}

val detailModule = listOf(viewModelModule, remoteModule, fragmentModule)

