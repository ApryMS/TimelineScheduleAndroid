package deploy.com.timelineschedule.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import deploy.com.timelineschedule.BaseActivity
import deploy.com.timelineschedule.R
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.home.HomeActivity
import deploy.com.timelineschedule.ui.login.LoginActivity

class SplashScreenActivity : BaseActivity(), SplashView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        SplashPresenter(this, PrefManager(this))
    }

    override fun nextPage(isLogin: Int) {
        Handler(Looper.myLooper()!!).postDelayed({
            if (isLogin == 1) startActivity(Intent(this, HomeActivity::class.java))
            else startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 2000)
    }

}