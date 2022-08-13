package deploy.com.timelineschedule.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.gson.Gson
import deploy.com.timelineschedule.R
import deploy.com.timelineschedule.databinding.ActivityHomeBinding
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.login.User

class HomeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        val pref = PrefManager(this)
//        val json = pref.getString("user_login")
//        val user = Gson().fromJson(json, User::class.java)
        binding.navView.setupWithNavController(
            findNavController(R.id.nav_host_fragment)
        )

    }
}