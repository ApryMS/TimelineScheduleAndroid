package deploy.com.timelineschedule.ui.dashboard.diskusi.detail

import com.google.gson.Gson
import deploy.com.timelineschedule.network.ApiService
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.home.addtimeline.AddTimelineResponse
import deploy.com.timelineschedule.ui.login.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DiskusiPresenter (
    private val view : ViewDiskusi,
    private val prefManager: PrefManager,
    private val apiService: ApiService
    ) {

    fun fetchDetailDiskusi(idDiskusi: String) {
        view.loading(true)
        GlobalScope.launch {
            val response = apiService.getDetailDiskusi(
                idDiskusi,
                "Bearer " + prefManager.getToken("token")
            )
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    response.body()?.let { view.detailDiskusi(it) }
                    view.loading(false)
                }
            } else {
                withContext(Dispatchers.Main) {
                    view.error("terjadi kesalahan")
                    view.loading(false)
                }
            }
        }
    }

    fun addKomentar(
        komentar: String,
        diskusiId: String
    ) {
        val json = prefManager.getString("user_login")
        val user =  Gson().fromJson(json, User::class.java)

        view.loading(true)
        GlobalScope.launch {
            val response = apiService.komentarDiskusi(
                diskusiId, user.id, komentar, "Bearer " + prefManager.getToken("token")
            )

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    response.body()?.let { view.addKomentar(it) }
                    view.loading(false)
                }
            } else {
                withContext(Dispatchers.Main) {
                    view.error("Terjadi Kesalahan")
                    view.loading(false)
                }
            }
        }
    }

    fun deleteDiskusi(idUser: String, idDiskusi: String) {
        view.loading(true)
        GlobalScope.launch {
            val response = apiService.deleteDiskusi(
                idUser, idDiskusi, "Bearer " + prefManager.getToken("token")
            )

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    response.body()?.let { view.deleteDiskusi(it) }
                    view.loading(false)
                }
            } else {
                withContext(Dispatchers.Main) {
                    view.error("Terjadi Kesalahan")
                    view.loading(false)
                }
            }
        }
    }

}

interface ViewDiskusi {
    fun loading(boolean: Boolean)
    fun detailDiskusi(responseDetailDiskusi: ResponseDetailDiskusi)
    fun addKomentar(responseKomentar: AddTimelineResponse)
    fun deleteDiskusi(deleteDiskusi: AddTimelineResponse)
    fun error(msg :String)
}