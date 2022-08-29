package deploy.com.timelineschedule.ui.home.addtimeline

import android.R
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.ImagePicker.Companion.REQUEST_CODE
import deploy.com.timelineschedule.BaseActivity
import deploy.com.timelineschedule.URIPathHelper
import deploy.com.timelineschedule.databinding.ActivityAddBinding
import deploy.com.timelineschedule.network.ApiClient
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.DataUser
import deploy.com.timelineschedule.ui.dashboard.DashboardActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class AddActivity : BaseActivity(), AddTimelineView {
    var FOTO_URI : Uri? = null
    private val binding by lazy { ActivityAddBinding.inflate(layoutInflater) }
    private lateinit var presenter : AddTimelinePresenter
    private lateinit var codes : ArrayList<String>
    var idEmployee : String? = null
    var kategori : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = AddTimelinePresenter(this, ApiClient.getService(), PrefManager(this))
        presenter.fetchEmployee()
        codes = ArrayList()
        binding.ivAddPhoto.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }
        val arrayAdapter = ArrayAdapter<String>(this, R.layout.simple_spinner_dropdown_item,
            arrayListOf(
                "Perangkat (Admin dan Server)",
                "Sistem dan Data (Admin dan Server)",
                "Perangkat POS",
                "Sistem dan Data POS" ,
                "Absensi",
                "Internet")
            )
        binding.etKategory.setAdapter(arrayAdapter)
        binding.etKategory.setOnItemClickListener { adapterView, view, i, l ->
            kategori = adapterView.getItemAtPosition(i).toString()
        }
    }
    override fun setupListener() {
        binding.btnSubmit.setOnClickListener {
            addTimeline()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            FOTO_URI= data.data
            Log.e("fileURi", FOTO_URI.toString())
            binding.ivAddPhoto.setImageURI(FOTO_URI)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun loading(loading: Boolean) {
        binding.btnSubmit.isEnabled = loading.not()
        if (loading) {
            binding.progressBarAdd.visibility = View.VISIBLE
            binding.btnSubmit.text = "Tunggu..."
        }
        else {
            binding.progressBarAdd.visibility = View.GONE
        }
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
    private fun addTimeline() {
        with(binding){
            if (kategori !== null && etDeskripsi.text !== null && idEmployee !== null && FOTO_URI !== null){
                val pathPhoto = URIPathHelper.getRealPath(this@AddActivity, FOTO_URI!!).toString()
                val imageFile = File(pathPhoto)
                Log.e("namaFile", imageFile.name)
                Log.e("cekImageActibiti", imageFile.toString())
                val imageRequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), imageFile)
                Log.e("namaFile", imageRequestBody.toString())
                val image = MultipartBody.Part.createFormData("file", imageFile.name, imageRequestBody)
                Log.e("cekImageActibiti", image.toString())
                val judul = RequestBody.create("text/plain".toMediaTypeOrNull(), kategori.toString())
                val deskripsi = RequestBody.create("text/plain".toMediaTypeOrNull(), etDeskripsi.text.toString().trim())
                val id_employe = RequestBody.create("text/plain".toMediaTypeOrNull(), idEmployee.toString())
                presenter.postTimeline(image, judul, deskripsi, id_employe
                )
            }
        }
    }

    override fun responseAdd(responseAdd: AddTimelineResponse) {
        Toast.makeText(applicationContext, responseAdd.message, Toast.LENGTH_SHORT).show()
        startActivity(Intent(applicationContext, DashboardActivity::class.java))
        finish()
    }
}