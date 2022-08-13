package deploy.com.timelineschedule.ui.registrasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.Gson
import deploy.com.timelineschedule.R
import deploy.com.timelineschedule.databinding.ActivityRegistrasiBinding
import deploy.com.timelineschedule.network.ApiClient
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.dashboard.DashboardActivity
import deploy.com.timelineschedule.ui.dashboard.DashboardITActivity
import deploy.com.timelineschedule.ui.home.HomeActivity
import deploy.com.timelineschedule.ui.login.ResponseLogin
import deploy.com.timelineschedule.ui.login.User

class RegistrasiActivity : AppCompatActivity(), RegisterView {
    private val binding by lazy { ActivityRegistrasiBinding.inflate(layoutInflater) }
    private lateinit var presenter: RegisterPresenter
    private lateinit var codes : ArrayList<String>
    var nameToko : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = RegisterPresenter(this, PrefManager(this), ApiClient.getService())
        presenter.fecthToko()
        codes = ArrayList()
        binding.btnSignin.setOnClickListener {
            with(binding) {
                if(etName.text != null && etPass.text != null && etEmail.text != null && nameToko.toString() != null ) {
                    presenter.postRegister(
                        etName.text.toString(),
                        etEmail.text.toString(),
                        etPass.text.toString(),
                        nameToko.toString()
                    )
                } else{
                    Toast.makeText(applicationContext, "Lengkapi data anda", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    override fun resToko(response: TokoResponse) {
        val data = response.data
        for (i in data.indices) {
            codes.add(data[i].nameToko)
        }
        val adapterToko = ArrayAdapter<String>(this, androidx.transition.R.layout.support_simple_spinner_dropdown_item, codes)
        binding.etToko.setAdapter(adapterToko)
        binding.etToko.setOnItemClickListener { adapterView, view, i, l ->
            nameToko = adapterView.getItemAtPosition(i).toString()
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

        val pref = PrefManager(baseContext)
        val json = pref.getString("user_login")
        val user = Gson().fromJson(json, User::class.java)
        if (user.position == "KARYAWAN") {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, DashboardITActivity::class.java))
            finish()
        }



    }


}