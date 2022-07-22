package deploy.com.timelineschedule.ui.home.detailtimeline


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import deploy.com.timelineschedule.BaseActivity
import deploy.com.timelineschedule.databinding.ActivityDetailBinding
import deploy.com.timelineschedule.network.ApiClient
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.home.addTask.AddTaskActivity
import deploy.com.timelineschedule.ui.login.User
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : BaseActivity() , DetailViewTImeline {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private lateinit var presenter: DetailTimelinePresenter
    private lateinit var adapterTask: TaskFromDetailAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = DetailTimelinePresenter(this, ApiClient.getService(), PrefManager(this))
        val id = intent.getStringExtra("idTimeline")!!
        presenter.fetchDetailTimeline(id)
        adapterTask = TaskFromDetailAdapter(arrayListOf(), object : TaskFromDetailAdapter.OnAdapterListener{
            override fun onClick(task: TaskItem) {

            }

        })
        binding.rvListTask.adapter = adapterTask


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
        binding.button.setOnClickListener {
            val intent = Intent(this@DetailActivity, AddTaskActivity::class.java)
            intent.putExtra("idTimeline", response.timeline.id)
            startActivity(intent)

        }
        val json = PrefManager(this).getString("user_login")
        val user =  Gson().fromJson(json, User::class.java)
        if (user.id == response.timeline.invite.id) {
            binding.button.visibility = View.VISIBLE

        } else{
            binding.button.visibility = View.GONE
        }
        adapterTask.addList(response.task)
        val outputFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US)
        val date = inputFormat.parse(response.timeline.createdAt)!!


        with(binding){
            tvName.text = response.timeline.name
            tvCreated.text = outputFormat.format(date)
            tvDescroptiom.text = response.timeline.description
            tvStatus.text = response.timeline.status
            tvNameInvite.text = response.timeline.invite.name
        }

    }

    override fun error(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}