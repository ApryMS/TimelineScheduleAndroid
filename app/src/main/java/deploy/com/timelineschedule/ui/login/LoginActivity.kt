package deploy.com.timelineschedule.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import deploy.com.timelineschedule.BaseActivity
import deploy.com.timelineschedule.databinding.ActivityLoginBinding
import deploy.com.timelineschedule.network.ApiClient
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.home.HomeActivity

class LoginActivity : BaseActivity(), LoginView {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private lateinit var presenter: LoginPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        presenter = LoginPresenter(this, ApiClient.getService(), PrefManager(this))

    }

    override fun setupListener() {
        with(binding){
            btnSignin.setOnClickListener {
                val user = LoginRequest()
                user.email = binding.etEmail.text.toString()
                user.password= binding.etPass.text.toString()
                presenter.fetchLogin(user)
            }
        }
    }

    override fun loginLoading(boolean: Boolean) {
        binding.btnSignin.isEnabled = boolean.not()
        when(boolean) {
            true -> binding.btnSignin.text = "Tunggu..."
            false -> binding.btnSignin.text = "Masuk"
        }
//        if(boolean) binding.progresbarrSignin.visibility = View.VISIBLE else binding.progresbarrSignin.visibility =View.GONE
    }

    override fun loginResponse(response: ResponseLogin) {
        presenter.saveLogin(response.data.user, response.data.token)
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun loginError(msg: String) {
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()

    }
}