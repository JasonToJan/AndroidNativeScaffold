package debug

import com.jason.homemodule.di.homeModule
import com.jason.mvvm.base.BaseApplication
import org.koin.core.context.startKoin

/**
 * @author Jason
 * @description->
 */
class HomeApplication : BaseApplication() {

    override fun initMethod() {
        super.initMethod()
        startKoin {
            modules(homeModule)
        }
    }

}