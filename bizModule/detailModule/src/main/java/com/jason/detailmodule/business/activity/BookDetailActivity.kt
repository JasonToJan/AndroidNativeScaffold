package com.jason.detailmodule.business.activity

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.jason.commonapplib.common.RouterConstant
import com.jason.detailmodule.R
import com.jason.detailmodule.business.fragment.BookDescFragment
import com.jason.detailmodule.business.fragment.PlayListFragment
import com.jason.detailmodule.databinding.BookDetailActivityBookBinding
import com.jason.detailmodule.http.DetailApi
import com.jason.detailmodule.model.BookDetailModel
import com.jason.homemodule.model.Book
import com.jason.mvvm.base.activity.BaseVmVBActivity
import com.jason.mvvm.base.viewmodel.BaseViewModel
import com.jason.mvvm.common.SimpleFragmentPagerAdapter
import org.koin.android.ext.android.inject

/**
 * @author Jason
 * @description->
 */
@Route(path = RouterConstant.DETAIL.DETAIL_MODULE_DETAIL_ROUTE)
class BookDetailActivity : BaseVmVBActivity<BookDetailViewModel, BookDetailActivityBookBinding>() {


    @Autowired(name = RouterConstant.KEYS.INTENT_DATA)
    @JvmField
    var bookId: String = ""

    @Autowired(name = RouterConstant.KEYS.INTENT_NAME)
    @JvmField
    var bookName: String = ""


    override fun isDIViewModel() = true

    private val bookFragment by inject<BookDescFragment>()
    private val playFragment by inject<PlayListFragment>()
    private val fragmentList = ArrayList<Fragment>()

    override fun initView() {
        super.initView()
        mTopBar?.setTitle(bookName)
        mViewModel.bookId = bookId
        val stringArray = resources.getStringArray(R.array.tab_list)
        fragmentList.add(playFragment)
        fragmentList.add(bookFragment)
        val simpleFragmentPagerAdapter =
            SimpleFragmentPagerAdapter(supportFragmentManager, fragmentList)
        mBinding.vpDetail.adapter = simpleFragmentPagerAdapter
        mBinding.tabDetail.setViewPager(mBinding.vpDetail, stringArray)
    }

    override fun initObserve() {

    }
}

class BookDetailViewModel(private val api: DetailApi) : BaseViewModel() {

    var bookId: String = ""

    val playListLiveData by lazy {
        MutableLiveData<BookDetailModel>()
    }

    val bookListLiveData by lazy {
        MutableLiveData<List<Book>>()
    }


    @Deprecated(message = "使用全局livedata观测")
    fun getBookPlayList(): LiveData<BookDetailModel> = emit {
        api.getBookDetail(bookId).resultData()
    }

    fun getPlayList() {
        launch {
            val resultData = api.getBookDetail(bookId).resultData()
            playListLiveData.postValue(resultData)
        }
    }

    fun getAboutList() {
        launch {
            val resultData = api.getAboutBook(bookId).resultData().recommend
            bookListLiveData.postValue(resultData)
        }
    }


}