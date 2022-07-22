package deploy.com.timelineschedule.ui.home.addTask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import deploy.com.timelineschedule.R
import deploy.com.timelineschedule.databinding.ActivityAddTaskBinding
import deploy.com.timelineschedule.network.ApiClient
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.DataUser
import deploy.com.timelineschedule.ui.home.addtimeline.InviteResponse
import deploy.com.timelineschedule.ui.home.detailtimeline.DetailActivity

class AddTaskActivity : AppCompatActivity(), AddTaskView {
    private val binding by lazy { ActivityAddTaskBinding.inflate(layoutInflater) }
    private lateinit var presenter: AddTaskPresenter
    private lateinit var codes : ArrayList<String>
    var idEmployee : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = AddTaskPresenter(this, ApiClient.getService(), PrefManager(this))
        presenter.fetchEmployeeWorker()
        codes = ArrayList()
        binding.btnSubmit.setOnClickListener {
            postTask()
        }
    }




    override fun error(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }





    override fun responseAddWorker(response: InviteResponse) {
        val dataEmployee = response.data
        for (i in dataEmployee.indices) {
            codes.add(dataEmployee[i].name)
        }
        val adapterInv =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, codes)
        binding.etInvite.setAdapter(adapterInv)
        binding.etInvite.setOnItemClickListener { adapterView, view, i, l ->
            presenter.fetchEmployeeByName(adapterView.getItemAtPosition(i).toString())
        }
    }

    override fun responseId(response: DataUser) {
        idEmployee = response.id
    }

    private fun postTask() {
        val idTimeline = intent.getStringExtra("idTimeline")
        with(binding){
            if (etTugas.text.toString() != null && idEmployee != null) {
                presenter.postTask(
                    etTugas.text.toString(),
                    idTimeline.toString(),
                    idEmployee.toString()
                )
            } else {
                Toast.makeText(applicationContext, "Mohon isi data dengan lengkap", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun responseAddTask(response: AddTaskResponse) {
        Toast.makeText(applicationContext, "Tugas Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("idTimeline", response.task.timeline)
        startActivity(intent)
        finish()
    }


    override fun loading(loading: Boolean) {
        if(loading) binding.pbLoading.visibility = View.GONE else  binding.pbLoading.visibility  = View.GONE
    }
}