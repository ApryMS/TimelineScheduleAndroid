package deploy.com.timelineschedule.ui.home.addtimeline

import android.R
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import deploy.com.timelineschedule.BaseActivity
import deploy.com.timelineschedule.databinding.ActivityAddBinding
import deploy.com.timelineschedule.network.ApiClient
import deploy.com.timelineschedule.ui.DataUser


class AddActivity : BaseActivity(), AddTimelineView {
    private val binding by lazy { ActivityAddBinding.inflate(layoutInflater) }
    private lateinit var presenter : AddTimelinePresenter
    private lateinit var codes : ArrayList<String>
    var idEmployee : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = AddTimelinePresenter(this, ApiClient.getService())
        presenter.fetchEmployee()
        codes = ArrayList()



    }

    override fun setupListener() {

    }

    override fun loading(loading: Boolean) {

    }

    override fun errorAdd(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun responseInvite(responseInvite: InviteResponse) {
        val dataEmployee = responseInvite.data
        for (i in dataEmployee.indices) {
            codes.add(dataEmployee[i].name)
        }
        val adapterInv =
            ArrayAdapter<String>(this, R.layout.simple_spinner_dropdown_item, codes)
        binding.etInvite.setAdapter(adapterInv)
        binding.etInvite.setOnItemClickListener { adapterView, view, i, l ->
            presenter.fetchEmployeeByName(adapterView.getItemAtPosition(i).toString())
        }

    }

    override fun responseId(resId: DataUser) {
        idEmployee = resId.id
    }


}