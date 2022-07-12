package deploy.com.timelineschedule.ui.splashscreen

import deploy.com.timelineschedule.preference.PrefManager

class SplashPresenter(
    private val view: SplashView,
    private val pref : PrefManager
){
    init {
        view.nextPage(pref.getInt("is_login"))
    }
}
interface SplashView{
    fun nextPage(isLogin: Int)
}