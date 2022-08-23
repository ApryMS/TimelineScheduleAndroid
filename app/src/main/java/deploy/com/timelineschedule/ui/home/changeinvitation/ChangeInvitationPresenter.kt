package deploy.com.timelineschedule.ui.home.changeinvitation

import com.google.gson.Gson
import deploy.com.timelineschedule.network.ApiService
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.DataUser
import deploy.com.timelineschedule.ui.dashboard.timeline.UpdateTimelineResponse
import deploy.com.timelineschedule.ui.home.addtimeline.InviteResponse
import deploy.com.timelineschedule.ui.login.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddTaskPresenter (
    private val view : AddTaskView,
    private val api : ApiService,
    private val prefManager: PrefManager
    ) {
    private val json = prefManager.getString("user_login")
    private val user = Gson().fromJson(json, User::class.java)
    fun fetchEmployeeWorker(){

        GlobalScope.launch {
            val response = api.getEmployeeWorker(user.id)
            if (response.isSuccessful){
                withContext(Dispatchers.Main){
                    response.body()?.let { view.responseAddWorker(it) }
                }
            } else {
                withContext(Dispatchers.Main){
                    view.error("Terjadi Kesalahan")
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

    fun changeInvitation(timeline: String,idInvite: String){
        GlobalScope.launch {
            view.loading(true)
            val response = api.changeInvite(
                timeline,
                idInvite,
                "Bearer " +prefManager.getToken("token")
            )

            if (response.isSuccessful){
                withContext(Dispatchers.Main) {
                    response.body()?.let { view.responseChangeInvite(it) }
                    view.loading(false)
                }
            } else {
                withContext(Dispatchers.Main) {
                    view.error("Terjadi kesalahan")
                    view.loading(false)
                }
            }
        }


    }



}
interface AddTaskView {
    fun loading(loading : Boolean)
    fun error (msg: String)
    fun responseChangeInvite(response: UpdateTimelineResponse)
    fun responseAddWorker(response: InviteResponse)
    fun responseId(response: DataUser)
}