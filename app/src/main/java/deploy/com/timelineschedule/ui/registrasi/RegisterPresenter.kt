package deploy.com.timelineschedule.ui.registrasi

import com.google.gson.Gson
import deploy.com.timelineschedule.network.ApiService
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.login.ResponseLogin
import deploy.com.timelineschedule.ui.login.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterPresenter (
    private var view: RegisterView,
    private var prefManager: PrefManager,
    private var api: ApiService
    ) {

    fun postRegister(
        name: String,
        email: String,
        password: String
    ){
        view.loading(true)
        GlobalScope.launch {
            val response = api.register(name, email, password)
            if (response.isSuccessful){
                withContext(Dispatchers.Main){
                    response.body()?.let { view.response(it) }
                    view.loading(false)
                }
            } else{
                withContext(Dispatchers.Main){
                    view.error("Terjadi kesalahan")
                    view.loading(false)
                }
            }
        }

    }

    fun saveLogin(data: User, token: String) {
        prefManager.put("is_login", 1)
        prefManager.put("token", token)
        prefManager.put("user_login", Gson().toJson(data))
    }

}

interface RegisterView{

    fun loading(loading: Boolean)
    fun error(msg: String)
    fun response(response: ResponseLogin)
}