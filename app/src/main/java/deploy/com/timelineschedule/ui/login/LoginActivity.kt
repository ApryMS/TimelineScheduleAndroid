package deploy.com.timelineschedule.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import deploy.com.timelineschedule.databinding.ActivityLoginBinding
import deploy.com.timelineschedule.network.ApiClient
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.home.HomeActivity
import org.json.JSONObject

class LoginActivity : AppCompatActivity(), LoginView {
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
        if(boolean) binding.progresbarrSignin.visibility = View.VISIBLE else binding.progresbarrSignin.visibility =View.GONE
    }

    override fun loginResponse(response: ResponseLogin) {
        presenter.saveLogin(response.data.user, response.data.token)
        startActivity(Intent(this,HomeActivity::class.java))
        finish()
    }

    override fun loginError(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}