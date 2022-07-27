package deploy.com.timelineschedule.ui.home.detailtimeline

import deploy.com.timelineschedule.network.ApiService
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.home.TimelineResponse
import deploy.com.timelineschedule.ui.task.UpdateTaskResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailTimelinePresenter (
    private val view: DetailViewTImeline,
    private val api: ApiService,
    private val pref: PrefManager
) {

    fun fetchDetailTimeline(id: String) {
        view.loading(true)
        GlobalScope.launch {
            val response = api.getDetailTimeline(id, "Bearer "+pref.getToken("token"))
            if (response.isSuccessful){
                withContext(Dispatchers.Main){
                    response.body()?.let { view.timelineDetailResponse(it) }
                    view.loading(false)
                }

            }else{
                withContext(Dispatchers.Main){
                    view.error("Terjadi Kesalahan mengambil detail timeline")
                    view.loading(false)
                }

            }
        }
    }

    fun updateTimeline(idTimeline: String) {
        view.loading(true)
        GlobalScope.launch {
            val res = api.updateTimeline(idTimeline, "Bearer "+pref.getToken("token"))
            if (res.isSuccessful) {
                withContext(Dispatchers.Main){
                    res.body()?.let { view.updateTimelineResponse(it) }
                    view.loading(false)
                }
            } else {
                withContext(Dispatchers.Main){
                    view.error("Terjadi kesalahan")
                    view.loading(false)
                }
            }
        }
    }
}

interface DetailViewTImeline {
    fun loading(boolean: Boolean)
    fun timelineDetailResponse(response: TimelineDetailResponse)
    fun updateTimelineResponse(responseUpdate: UpdateTaskResponse)
    fun error(msg :String)

}
