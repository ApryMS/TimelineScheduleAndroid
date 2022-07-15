package deploy.com.timelineschedule.ui.home

import com.google.gson.Gson
import deploy.com.timelineschedule.network.ApiService
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.login.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class TimelinePresenter (
        private  val view: TimelineView,
        private  val pref: PrefManager,
        private val api : ApiService
    ) {

    init {
//        Log.e("Profile", pref.getString("user_login")!!)
    }

    fun date() {
        val format: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date: Date = format.parse("2015-06-25T00:00:00.000Z")
    }

    fun fetchTimelineByIdStatus(status: String) {
        val json = pref.getString("user_login")
        val user =  Gson().fromJson(json, User::class.java)
        view.loading(true)
        GlobalScope.launch {
            val response = api.getTimelineWithStatus(user.id, status, "Bearer "+pref.getToken("token"))
            if (response.code() == 401){
                withContext(Dispatchers.Main) {
                    response.body()?.let { view.timelineResponse(it) }
                    view.loading(false)
                }
            }
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    response.body()?.let { view.timelineResponse(it) }
                    view.loading(false)
                }
            }else {
                withContext(Dispatchers.Main) {
                    view.error("Tidak Ada Data")
                    view.loading(false)
                }

            }
        }

    }

    fun logout(){
        pref.clearData()
        view.logout()
    }
}

interface TimelineView {
    fun loading(boolean: Boolean)
    fun timelineResponse(response: TimelineResponse)
    fun error(msg :String)
    fun logout()
}