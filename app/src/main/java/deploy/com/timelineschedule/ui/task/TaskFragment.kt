package deploy.com.timelineschedule.ui.task

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import deploy.com.timelineschedule.R
import deploy.com.timelineschedule.databinding.FragmentTaskBinding
import deploy.com.timelineschedule.network.ApiClient
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.home.addtimeline.AddActivity


class TaskFragment : Fragment(), ViewTask {
    private lateinit var binding: FragmentTaskBinding
    private lateinit var presenter: TaskPresent
    private lateinit var taskAdapter: TaskAdapter
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        presenter = TaskPresent(ApiClient.getService(), this, PrefManager(requireActivity()))
        return  binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        taskAdapter = TaskAdapter(arrayListOf(), object : TaskAdapter.OnAdapterListener{
            override fun onClick(task: TaskItem) {
                val intent = Intent(requireActivity(), DetailTaskActivity::class.java)
                intent.putExtra("idTask", task.id)
                startActivity(intent)
            }

        })
        binding.rvTimeline.adapter = taskAdapter

        with(binding) {

            presenter.fetchTask("HOLD")
            binding.swipe.setOnRefreshListener {
                presenter.fetchTask("HOLD")
            }

            btnFinish.setOnClickListener {
                btnFinish.setBackgroundResource(R.drawable.shape_ractengle_button)
                btnFinish.setTextColor(Color.WHITE)
                btnProcess.setBackgroundResource(R.drawable.shape_ractangle_button_un)
                btnProcess.setTextColor(Color.BLACK)
                presenter.fetchTask("FINISHED")
                binding.swipe.setOnRefreshListener {
                    presenter.fetchTask("FINISHED")
                }
            }
            btnProcess.setOnClickListener {
                btnFinish.setBackgroundResource(R.drawable.shape_ractangle_button_un)
                btnFinish.setTextColor(Color.BLACK)
                btnProcess.setBackgroundResource(R.drawable.shape_ractengle_button)
                btnProcess.setTextColor(Color.WHITE)
                presenter.fetchTask("HOLD")
                binding.swipe.setOnRefreshListener {
                    presenter.fetchTask("HOLD")
                }

            }
        }
    }


    override fun loading(loading: Boolean) {
        binding.swipe.isRefreshing = loading
        if(loading) binding.progressBar.visibility = View.GONE else  binding.progressBar.visibility  = View.GONE
    }

    override fun error(msg: String) {
        binding.rvTimeline.visibility = View.GONE
        binding.imgNoData.visibility = View.VISIBLE
        binding.txtNodata.visibility = View.VISIBLE
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun responseTask(response: GetTaskResponse) {
        if (response.task.isNotEmpty()) {
            taskAdapter.addList(response.task)
            binding.rvTimeline.visibility = View.VISIBLE
            binding.imgNoData.visibility = View.GONE
            binding.txtNodata.visibility = View.GONE
        } else {
            binding.rvTimeline.visibility = View.GONE
            binding.imgNoData.visibility = View.VISIBLE
            binding.txtNodata.visibility = View.VISIBLE
        }
    }

}