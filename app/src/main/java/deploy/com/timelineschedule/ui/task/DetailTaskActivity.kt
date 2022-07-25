package deploy.com.timelineschedule.ui.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import deploy.com.timelineschedule.R
import deploy.com.timelineschedule.databinding.ActivityAddTaskBinding
import deploy.com.timelineschedule.databinding.ActivityDetailTaskBinding
import deploy.com.timelineschedule.network.ApiClient
import deploy.com.timelineschedule.preference.PrefManager
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DetailTaskActivity : AppCompatActivity(), ViewDetailTask {
    private val binding by lazy { ActivityDetailTaskBinding.inflate(layoutInflater) }
    private lateinit var presenter: DetailTaskPresenter
    private lateinit var dataId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = DetailTaskPresenter(this, ApiClient.getService(), PrefManager(this))
        dataId = intent.getStringExtra("idTask").toString()
        presenter.getDetailTask(dataId)


    }


    override fun loading(loading: Boolean) {
        if (loading) binding.pbLoading.visibility = View.VISIBLE else binding.pbLoading.visibility = View.GONE
    }

    override fun error(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    override fun response(response: DetailTaskResponse) {
        val task = response.data
        if (task.status == "HOLD"){
            binding.tvStatus.setTextColor(getColor(R.color.red))
            binding.view.setBackgroundColor(getColor(R.color.red))
            binding.button.isEnabled = true
        }
        if(task.status == "FINISHED") {
            binding.tvStatus.setTextColor(getColor(R.color.green))
            binding.view.setBackgroundColor(getColor(R.color.green))
            binding.button.isEnabled = false
        }
        val outputFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US)
        val dateInvite = inputFormat.parse(task.createdAt)
        val dateTimeline = inputFormat.parse(task.timeline.createdAt)

        with(binding){
            tvTugas.text = task.name
            tvStatus.text = task.status
            tvNameOwner.text = response.data.timeline.makeBy.name
            tvNameInvite.text = task.inviteBy.name
            tvDateInvite.text = outputFormat.format(dateInvite)
            tvDateTimeline.text = outputFormat.format(dateTimeline)
        }
        binding.button.setOnClickListener {
            updateStatusTask(task.id)
        }
    }

    private fun updateStatusTask(idTask: String) {
        presenter.updateTask(idTask,"FINISHED")
    }

    override fun responseUpdate(responseUpdate: UpdateTaskResponse) {
        Toast.makeText(applicationContext, responseUpdate.message, Toast.LENGTH_SHORT).show()
    }

}