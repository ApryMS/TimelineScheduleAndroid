package deploy.com.timelineschedule.ui.home.addtimeline

import android.util.Log
import com.google.gson.Gson
import deploy.com.timelineschedule.network.ApiService
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.DataUser
import deploy.com.timelineschedule.ui.login.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddTimelinePresenter (
    private val view : AddTimelineView,
    private val api : ApiService,
    private val prefManager: PrefManager
) {
    init {
        view.setupListener()
    }

    fun fetchEmployee(){
        val json = prefManager.getString("user_login")
        val user =  Gson().fromJson(json, User::class.java)
        GlobalScope.launch {
            val res = api.getEmployee(user.id)
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
        image: MultipartBody.Part?, name: RequestBody, description : RequestBody, invite : RequestBody,
    ) {
        view.loading(true)
        val json = prefManager.getString("user_login")
        val user =  Gson().fromJson(json, User::class.java)
        val user_id = RequestBody.create("text/plain".toMediaTypeOrNull(), user.id)
        GlobalScope.launch {
            val response = api.postTimeline(
                "Bearer "+prefManager.getToken("token") ,image!!, name,user_id,description,invite
            )
            if(response.code()==401){
                withContext(Dispatchers.Main){
                    response.body()?.let { view.errorAdd(it.message) }
                    view.loading(false)
                }
            }
            if (response.isSuccessful) {
                withContext(Dispatchers.Main){
                    response.body()?.let { view.responseAdd(it) }
                    view.loading(false)
                }
            }else {
                withContext(Dispatchers.Main){
                    view.errorAdd("terjadi kesalahan")
                    view.loading(false)
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