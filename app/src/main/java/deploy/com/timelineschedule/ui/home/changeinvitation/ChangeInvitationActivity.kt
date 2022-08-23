package deploy.com.timelineschedule.ui.home.changeinvitation

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import deploy.com.timelineschedule.R
import deploy.com.timelineschedule.databinding.ActivityAddTaskBinding
import deploy.com.timelineschedule.network.ApiClient
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.DataUser
import deploy.com.timelineschedule.ui.dashboard.DashboardITActivity
import deploy.com.timelineschedule.ui.dashboard.timeline.UpdateTimelineResponse
import deploy.com.timelineschedule.ui.home.addtimeline.InviteResponse

class ChangeInvitationActivity : AppCompatActivity(), AddTaskView {
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
            changeInvitation()
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

    private fun changeInvitation() {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_confirm_change_invitation, null)
        builder.setView(view)
        builder.setPositiveButton("Yakin", DialogInterface.OnClickListener { dialog, which ->
            val idTimeline = intent.getStringExtra("idTimeline")
            presenter.changeInvitation(idTimeline.toString(), idEmployee.toString())
        })
        builder.setNegativeButton(
            "keluar",
            DialogInterface.OnClickListener { dialog, which -> })
        builder.show()

    }
    override fun responseChangeInvite(response: UpdateTimelineResponse) {
        Toast.makeText(applicationContext, "Mengganti Invitation Berhasil", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, DashboardITActivity::class.java))
        finish()
    }


    override fun loading(loading: Boolean) {
        if(loading) binding.pbLoading.visibility = View.GONE else  binding.pbLoading.visibility  = View.GONE
    }
}