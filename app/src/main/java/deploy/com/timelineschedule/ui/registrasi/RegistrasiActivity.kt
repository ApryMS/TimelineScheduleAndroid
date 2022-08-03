package deploy.com.timelineschedule.ui.registrasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import deploy.com.timelineschedule.R
import deploy.com.timelineschedule.databinding.ActivityRegistrasiBinding
import deploy.com.timelineschedule.network.ApiClient
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.home.HomeActivity
import deploy.com.timelineschedule.ui.login.ResponseLogin

class RegistrasiActivity : AppCompatActivity(), RegisterView {
    private lateinit var valueSue : String
    private val binding by lazy { ActivityRegistrasiBinding.inflate(layoutInflater) }
    private lateinit var presenter: RegisterPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = RegisterPresenter(this, PrefManager(this), ApiClient.getService())
        val position = resources.getStringArray(R.array.position)
        val arrayAdapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, position)
        binding.etPosition.setAdapter(arrayAdapter)
        valueSue = String()
        binding.etPosition.setOnItemClickListener { adapterView, view, i, l ->
            valueSue = adapterView.getItemAtPosition(i).toString()

        }
        binding.btnSignin.setOnClickListener {
            with(binding) {
                if(etName.text != null && etPass.text != null && etEmail.text != null && valueSue != null) {
                    presenter.postRegister(
                        etName.text.toString(),
                        etEmail.text.toString(),
                        etPass.text.toString(),
                        valueSue,
                    )
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    finish()
                } else{
                    Toast.makeText(applicationContext, "Lengkapi data anda", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun loading(loading: Boolean) {
        binding.btnSignin.isEnabled = loading.not()
        when(loading) {
            true -> binding.btnSignin.text = "Tunggu..."
            false -> binding.btnSignin.text = "Registrasi"
        }
    }

    override fun error(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    override fun response(response: ResponseLogin) {
        presenter.saveLogin(response.data.user, response.data.token)
        Toast.makeText(applicationContext, response.message, Toast.LENGTH_SHORT).show()
        startActivity(Intent(applicationContext, HomeActivity::class.java))
    }
}