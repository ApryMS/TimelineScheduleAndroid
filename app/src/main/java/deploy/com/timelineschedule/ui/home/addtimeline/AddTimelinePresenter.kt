package deploy.com.timelineschedule.ui.home.addtimeline

import deploy.com.timelineschedule.network.ApiService
import deploy.com.timelineschedule.ui.DataUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddTimelinePresenter (
    private val view : AddTimelineView,
    private val api : ApiService
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
}
interface AddTimelineView {
    fun setupListener()
    fun loading(loading: Boolean)
    fun errorAdd(msg : String)
//    fun responseAdd(responseAdd : )
    fun responseInvite(responseInvite: InviteResponse)
    fun responseId(resId: DataUser)
}