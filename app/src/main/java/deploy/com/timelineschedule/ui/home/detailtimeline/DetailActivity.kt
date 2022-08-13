package deploy.com.timelineschedule.ui.home.detailtimeline


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import deploy.com.timelineschedule.BaseActivity
import deploy.com.timelineschedule.R
import deploy.com.timelineschedule.databinding.ActivityDetailBinding
import deploy.com.timelineschedule.network.ApiClient
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.dashboard.DashboardActivity
import deploy.com.timelineschedule.ui.dashboard.DashboardITActivity
import deploy.com.timelineschedule.ui.home.HomeActivity
import deploy.com.timelineschedule.ui.home.addTask.AddTaskActivity
import deploy.com.timelineschedule.ui.login.User
import deploy.com.timelineschedule.ui.task.UpdateTaskResponse
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : BaseActivity() , DetailViewTImeline {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private lateinit var presenter: DetailTimelinePresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = DetailTimelinePresenter(this, ApiClient.getService(), PrefManager(this))
        val id = intent.getStringExtra("idTimeline")!!
        presenter.fetchDetailTimeline(id)

    }


    override fun loading(boolean: Boolean) {
        if (boolean) {
            with(binding){
                pbLoading.visibility = View.VISIBLE
                cvDataInvite.visibility = View.GONE
                cvDataOwner.visibility = View.GONE
            }
        }else{
            with(binding){
                pbLoading.visibility = View.GONE
                cvDataInvite.visibility = View.VISIBLE
                cvDataOwner.visibility = View.VISIBLE
            }
        }
    }

    override fun timelineDetailResponse(response: TimelineDetailResponse) {

        binding.btnFinishedTimeline.visibility = View.VISIBLE

        val outputFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US)
        if (response.timeline.status == "HOLD"){
            binding.btnFinishedTimeline.isEnabled = true
            binding.textView10.visibility = View.GONE
            binding.tvTglSelesai.visibility = View.GONE
        } else {
            binding.btnFinishedTimeline.isEnabled = false
            binding.textView10.visibility = View.VISIBLE
            binding.tvTglSelesai.visibility = View.VISIBLE
            val dateFinish = inputFormat.parse(response.timeline.updatedAt)!!
            binding.tvTglSelesai.text = outputFormat.format(dateFinish)

        }
        binding.button.setOnClickListener {
            val intent = Intent(this@DetailActivity, AddTaskActivity::class.java)
            intent.putExtra("idTimeline", response.timeline.id)
            startActivity(intent)

        }
        binding.btnFinishedTimeline.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.dialog_confirm, null)
            builder.setView(view)
            builder.setPositiveButton("Sudah", DialogInterface.OnClickListener { dialog, which ->
                presenter.updateTimeline(response.timeline.id)


            })
            builder.setNegativeButton(
                "Belum",
                DialogInterface.OnClickListener { dialog, which -> })
            builder.show()
        }
        val json = PrefManager(this).getString("user_login")
        val user =  Gson().fromJson(json, User::class.java)
        if (user.id == response.timeline.invite.id) {
            binding.button.visibility = View.VISIBLE


        } else{
            binding.button.visibility = View.GONE

        }

        val date = inputFormat.parse(response.timeline.createdAt)!!


        with(binding){
            tvName.text = response.timeline.name
            tvCreated.text = outputFormat.format(date)
            tvDescroptiom.text = response.timeline.description
            tvStatus.text = response.timeline.status
            tvNameInvite.text = response.timeline.invite.name
        }

    }

    override fun updateTimelineResponse(responseUpdate: UpdateTaskResponse) {
        val json = PrefManager(this).getString("user_login")
        val user =  Gson().fromJson(json, User::class.java)
        Toast.makeText(applicationContext, responseUpdate.message, Toast.LENGTH_SHORT).show()
        if (user.position == "KARYAWAN") {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, DashboardITActivity::class.java))
            finish()
        }

    }

    override fun error(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}