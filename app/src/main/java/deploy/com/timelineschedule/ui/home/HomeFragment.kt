package deploy.com.timelineschedule.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import deploy.com.timelineschedule.R
import deploy.com.timelineschedule.databinding.FragmentHomeBinding
import deploy.com.timelineschedule.network.ApiClient
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.dashboard.timeline.*
import deploy.com.timelineschedule.ui.home.addtimeline.AddActivity
import deploy.com.timelineschedule.ui.home.detailtimeline.DetailActivity

class HomeFragment : Fragment(), TimelineView {
    private lateinit var  binding: FragmentHomeBinding
    private lateinit var  presenter: TimelinePresenter
    private lateinit var timelineAdapter: TimelineAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        presenter = TimelinePresenter(this, PrefManager(requireContext()), ApiClient.getService())

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        timelineAdapter = TimelineAdapter(arrayListOf(), object : TimelineAdapter.OnAdapterListener{
            override fun onClick(timeline: TimelineItem) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra("idTimeline", timeline.id)
                startActivity(intent)
            }

        })
        binding.rvTimeline.adapter = timelineAdapter
        with(binding) {
            presenter.fetchTimelineByIdStatus("HOLD")
            btnFinish.setOnClickListener {
                btnFinish.setBackgroundResource(R.drawable.shape_ractengle_button)
                btnFinish.setTextColor(Color.WHITE)
                btnProcess.setBackgroundResource(R.drawable.shape_ractangle_button_un)
                btnProcess.setTextColor(Color.BLACK)
                presenter.fetchTimelineByIdStatus("FINISHED")
            }
            btnProcess.setOnClickListener {
                btnFinish.setBackgroundResource(R.drawable.shape_ractangle_button_un)
                btnFinish.setTextColor(Color.BLACK)
                btnProcess.setBackgroundResource(R.drawable.shape_ractengle_button)
                btnProcess.setTextColor(Color.WHITE)
                presenter.fetchTimelineByIdStatus("HOLD")
            }

            fabAdd.setOnClickListener {
                startActivity(Intent(requireActivity(), AddActivity::class.java))
            }
        }
    }

    override fun loading(boolean: Boolean) {
        if(boolean) binding.progressBar.visibility = View.GONE else  binding.progressBar.visibility  = View.GONE
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
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }


    override fun logout() {
        Toast.makeText(requireActivity(), "Berhasil Keluar" , Toast.LENGTH_SHORT).show()
    }

}