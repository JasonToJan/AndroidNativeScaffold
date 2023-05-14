package debug

import com.jason.catmodule.di.catModule
import com.jason.mvvm.base.BaseApplication
import org.koin.core.context.startKoin

/**
 * @author Jason
 * @description->
 */
class CatApplication : BaseApplication() {

    override fun initMethod() {
        super.initMethod()
        startKoin {
            modules(catModule)
        }
    }

}