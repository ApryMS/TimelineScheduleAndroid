package deploy.com.timelineschedule.ui.task

import android.util.Log
import deploy.com.timelineschedule.network.ApiService
import deploy.com.timelineschedule.preference.PrefManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailTaskPresenter (
    private val view : ViewDetailTask,
    private val api : ApiService,
    private val pref : PrefManager
    ) {

    fun getDetailTask(idTask: String) {
        view.loading(true)
        GlobalScope.launch {
            val response = api.getDetailTask(
                idTask,
                "Bearer "+pref.getToken("token")
            )
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    view.loading(false)
                    response.body()?.let { view.response(it) }
                }
            } else {
                withContext(Dispatchers.Main){
                    view.loading(false)
                    view.error("Terjadi Kesalahan")
                }
            }
        }
    }

    fun updateTask(idTask: String, status: String) {
        view.loading(true)
        GlobalScope.launch {
            val response = api.updateTask(idTask,status, "Bearer "+pref.getToken("token"))
            Log.e("idTask", idTask)
            Log.e("toktoken", "Bearer "+pref.getToken("token"))
            Log.e("statusS", status)
            if (response.isSuccessful){
                withContext(Dispatchers.Main) {
                    view.loading(false)
                    response.body()?.let { view.responseUpdate(it) }
                }
            }else{
                withContext(Dispatchers.Main) {
                    view.error("Terjadi kesalahan")
                }
            }
        }
    }
}
interface ViewDetailTask {
    fun loading (loading: Boolean)
    fun error (msg: String)
    fun response (response : DetailTaskResponse)
    fun responseUpdate(responseUpdate : UpdateTaskResponse)

}