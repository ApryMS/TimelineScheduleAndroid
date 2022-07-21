package deploy.com.timelineschedule.ui.home.addtimeline

import com.google.gson.Gson
import deploy.com.timelineschedule.network.ApiService
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.DataUser
import deploy.com.timelineschedule.ui.home.detailtimeline.MakeBy
import deploy.com.timelineschedule.ui.login.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddTimelinePresenter (
    private val view : AddTimelineView,
    private val api : ApiService,
    private val prefManager: PrefManager
) {
    init {
        view.setupListener()
    }

    fun fetchEmployee(){
        GlobalScope.launch {
            val res = api.getEmployee()
            if (res.isSuccessful){
                withContext(Dispatchers.Main){
                    res.body()?.let { view.responseInvite(it) }
                }
            }else {
                withContext(Dispatchers.Main){
                    view.errorAdd("Tidak Ada Pekerja")
                }
            }
        }
    }

    fun fetchEmployeeByName(name: String){
        GlobalScope.launch {
            val data = api.getEmployeeByName(name)
            if (data.isSuccessful){
                withContext(Dispatchers.Main){
                    val user = data.body()
                    user?.let { view.responseId(it) }

                }
            }
        }
    }

    fun postTimeline(
        name: String,
        description : String,
        invite : String
    ) {
        val json = prefManager.getString("user_login")
        val user =  Gson().fromJson(json, User::class.java)
        GlobalScope.launch {
            val response = api.postTimeline(
                name,user.id,description,invite,"Bearer "+prefManager.getToken("token")
            )
            if(response.code()==401){
                withContext(Dispatchers.Main){
                    response.body()?.let { view.errorAdd(it.message) }
                }
            }
            if (response.isSuccessful) {
                withContext(Dispatchers.Main){
                    response.body()?.let { view.responseAdd(it) }
                }
            }else {
                withContext(Dispatchers.Main){
                    view.errorAdd("terjadi kesalahan")
                }
            }


        }

    }
}
interface AddTimelineView {
    fun setupListener()
    fun loading(loading: Boolean)
    fun errorAdd(msg : String)
    fun responseAdd(responseAdd : AddTimelineResponse)
    fun responseInvite(responseInvite: InviteResponse)
    fun responseId(resId: DataUser)
}