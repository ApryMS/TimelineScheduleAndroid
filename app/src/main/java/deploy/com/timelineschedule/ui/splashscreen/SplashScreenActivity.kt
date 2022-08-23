package deploy.com.timelineschedule.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import deploy.com.timelineschedule.BaseActivity
import deploy.com.timelineschedule.R
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.dashboard.DashboardActivity
import deploy.com.timelineschedule.ui.dashboard.DashboardITActivity
import deploy.com.timelineschedule.ui.login.LoginActivity
import deploy.com.timelineschedule.ui.login.User

class SplashScreenActivity : BaseActivity(), SplashView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        SplashPresenter(this, PrefManager(this))
    }

    override fun nextPage(isLogin: Int) {
        Handler(Looper.myLooper()!!).postDelayed({
            if (isLogin == 1) {
                val pref = PrefManager(this)
                val json = pref.getString("user_login")
                val user = Gson().fromJson(json, User::class.java)
                if (user.position == "KARYAWAN"){
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this, DashboardITActivity::class.java))
                    finish()
                }
            }
            else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }, 2000)
    }

}