package deploy.com.timelineschedule.ui.home.detailtimeline

import deploy.com.timelineschedule.network.ApiService
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.home.TimelineResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailTimelinePresenter (
    private val view: DetailViewTImeline,
    private val api: ApiService,
    private val pref: PrefManager
) {
    init {
        view.setupListener()
    }

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
}

interface DetailViewTImeline {
    fun setupListener()
    fun loading(boolean: Boolean)
    fun timelineDetailResponse(response: TimelineDetailResponse)
    fun error(msg :String)

}
