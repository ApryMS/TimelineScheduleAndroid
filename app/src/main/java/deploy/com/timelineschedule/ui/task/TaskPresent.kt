package deploy.com.timelineschedule.ui.task

import com.google.gson.Gson
import deploy.com.timelineschedule.network.ApiService
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.login.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskPresent (
        private val api : ApiService,
        private val view : ViewTask,
        private val prefManager: PrefManager
        ) {
        private val json = prefManager.getString("user_login")
        private val user =  Gson().fromJson(json, User::class.java)
        fun fetchTask(status: String) {
                view.loading(true)
                GlobalScope.launch {
                        val response = api.getTaskByUserAndStatus(user.id,status, "Bearer "+prefManager.getToken("token"))
                        if (response.isSuccessful) {
                                withContext(Dispatchers.Main) {
                                        response.body()?.let { view.responseTask(it) }
                                        view.loading(false)
                                }
                        } else {
                                withContext(Dispatchers.Main) {
                                        view.error("Tidak Ada Data")
                                        view.loading(false)
                                }

                        }
                }
        }
}

interface ViewTask {
        fun loading (loading: Boolean)
        fun error (msg: String)
        fun responseTask(response: GetTaskResponse)
}