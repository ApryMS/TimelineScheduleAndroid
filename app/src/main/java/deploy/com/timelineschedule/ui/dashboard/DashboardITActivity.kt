package deploy.com.timelineschedule.ui.dashboard

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import deploy.com.timelineschedule.R
import deploy.com.timelineschedule.databinding.ActivityDashboardItactivityBinding
import deploy.com.timelineschedule.network.ApiClient
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.dashboard.timeline.*
import deploy.com.timelineschedule.ui.home.detailtimeline.DetailActivity
import deploy.com.timelineschedule.ui.login.LoginActivity
import deploy.com.timelineschedule.ui.login.User

class DashboardITActivity : AppCompatActivity(), TimelineView {
    private val binding by lazy { ActivityDashboardItactivityBinding.inflate(layoutInflater) }
    private lateinit var presenter : TimelinePresenter
    private lateinit var timelineAdapter: TimelineAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        presenter = TimelinePresenter(this, PrefManager(this), ApiClient.getService())
        val pref = PrefManager(this)
        val json = pref.getString("user_login")
        val user = Gson().fromJson(json, User::class.java)


        binding.tvName.text = user.name
        binding.tvToko.text = user.position

        Log.d("cekPos", user.position)

        binding.imageView2.setOnClickListener {
            pref.clearData()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        timelineAdapter = TimelineAdapter(arrayListOf(), object : TimelineAdapter.OnAdapterListener{
            override fun onClick(timeline: TimelineItem) {
                val intent = Intent(this@DashboardITActivity , DetailActivity::class.java)
                intent.putExtra("idTimeline", timeline.id)
                startActivity(intent)
            }

        })
        binding.rvTimeline.adapter = timelineAdapter
        with(binding) {
            swipeIt.setOnRefreshListener {
                presenter.fetchTimelineByIdStatus("HOLD")
            }
            presenter.fetchTimelineByIdStatus("HOLD")
            btnFinish.setOnClickListener {
                swipeIt.setOnRefreshListener {
                    presenter.fetchTimelineByIdStatus("FINISHED")
                }
                btnFinish.setBackgroundResource(R.drawable.shape_ractengle_button)
                btnFinish.setTextColor(Color.WHITE)
                btnProcess.setBackgroundResource(R.drawable.shape_ractangle_button_un)
                btnProcess.setTextColor(Color.BLACK)
                presenter.fetchTimelineByIdStatus("FINISHED")
            }
            btnProcess.setOnClickListener {
                swipeIt.setOnRefreshListener {
                    presenter.fetchTimelineByIdStatus("HOLD")
                }
                btnFinish.setBackgroundResource(R.drawable.shape_ractangle_button_un)
                btnFinish.setTextColor(Color.BLACK)
                btnProcess.setBackgroundResource(R.drawable.shape_ractengle_button)
                btnProcess.setTextColor(Color.WHITE)
                presenter.fetchTimelineByIdStatus("HOLD")
            }
        }


    }

    override fun loading(boolean: Boolean) {
        binding.swipeIt.isRefreshing = boolean
        if(boolean) {
            binding.progressBar.visibility = View.VISIBLE
            binding.imgNoData.visibility = View.GONE
            binding.rvTimeline.visibility = View.GONE

        } else {
            binding.progressBar.visibility = View.GONE

        }
    }

    override fun timelineResponse(response: TimelineResponse) {
        if (response.timeline.isNotEmpty()) {
            timelineAdapter.addList(response.timeline)
            binding.rvTimeline.visibility = View.VISIBLE
            binding.imgNoData.visibility = View.GONE
            binding.txtNodata.visibility = View.GONE
        } else {
            binding.rvTimeline.visibility = View.GONE
            binding.imgNoData.visibility = View.VISIBLE
            binding.txtNodata.visibility = View.VISIBLE
        }


    }

    override fun error(msg: String) {
        binding.rvTimeline.visibility = View.GONE
        binding.imgNoData.visibility = View.VISIBLE
        binding.txtNodata.visibility = View.VISIBLE
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }


    override fun logout() {
        Toast.makeText(applicationContext, "Berhasil Keluar" , Toast.LENGTH_SHORT).show()
    }
}