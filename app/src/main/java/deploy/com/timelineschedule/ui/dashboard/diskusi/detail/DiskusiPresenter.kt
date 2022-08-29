package deploy.com.timelineschedule.ui.dashboard.diskusi.detail

import deploy.com.timelineschedule.network.ApiService
import deploy.com.timelineschedule.preference.PrefManager
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

}

interface ViewDiskusi {
    fun loading(boolean: Boolean)
    fun detailDiskusi(responseDetailDiskusi: ResponseDetailDiskusi)
    fun error(msg :String)
}