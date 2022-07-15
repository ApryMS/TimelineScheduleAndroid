package deploy.com.timelineschedule.ui.login

import com.google.gson.Gson
import deploy.com.timelineschedule.network.ApiService
import deploy.com.timelineschedule.preference.PrefManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginPresenter (
    private val view :LoginView,
    private val api :ApiService,
    private val pref : PrefManager
        ) {
    init {
        view.setupListener()
    }
    fun fetchLogin(req:LoginRequest){
        view.loginLoading(true)
        GlobalScope.launch {
            val response = api.login(req)
                if (response.isSuccessful){
                    if (response.body()!!.status){
                        withContext(Dispatchers.Main){
                            view.loginResponse(response.body()!!)
                            view.loginLoading(false)
                        }
                    }else{
                        withContext(Dispatchers.Main){
                            view.loginError(response.body()!!.message)
                            view.loginLoading(false)
                        }

                    }
                } else {
                    view.loginError("Terjadi Kesalahan")
                }
        }
    }

    fun saveLogin(data: User, token: String) {
        pref.put("is_login", 1)
        pref.put("token", token)
        pref.put("user_login", Gson().toJson(data))
    }


}
interface LoginView{
    fun setupListener()
    fun loginLoading(boolean: Boolean)
    fun loginResponse(response: ResponseLogin)
    fun loginError(msg: String)
}