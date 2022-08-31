package deploy.com.timelineschedule.ui.dashboard.diskusi.detail

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import deploy.com.timelineschedule.R
import deploy.com.timelineschedule.databinding.ActivityDetailDiskusiBinding
import deploy.com.timelineschedule.network.ApiClient
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.dashboard.DashboardITActivity
import deploy.com.timelineschedule.ui.dashboard.diskusi.KomentarAdapter
import deploy.com.timelineschedule.ui.dashboard.timeline.TimelineAdapter
import deploy.com.timelineschedule.ui.dashboard.timeline.TimelineItem
import deploy.com.timelineschedule.ui.home.addtimeline.AddTimelineResponse
import deploy.com.timelineschedule.ui.home.detailtimeline.DetailActivity
import deploy.com.timelineschedule.ui.login.User

class DetailDiskusiActivity : AppCompatActivity(), ViewDiskusi {
    private val binding by lazy{ ActivityDetailDiskusiBinding.inflate(layoutInflater)}
    private lateinit var presenter: DiskusiPresenter
    private lateinit var adapterKomentar: KomentarAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = DiskusiPresenter(this, PrefManager(this), ApiClient.getService())
        val diskusi = intent.getStringExtra("idDiskusi")
        presenter.fetchDetailDiskusi(diskusi!!)
        adapterKomentar = KomentarAdapter(arrayListOf(), object : KomentarAdapter.OnAdapterListener{
            override fun onClick(komentar: KomentarItem) {
            }

        })
        binding.rvKomentar.adapter = adapterKomentar


    }

    override fun loading(boolean: Boolean) {
        if (boolean) binding.pbDiskusi.visibility = View.VISIBLE else binding.pbDiskusi.visibility = View.GONE
    }

    override fun detailDiskusi(responseDetailDiskusi: ResponseDetailDiskusi) {
        val json = PrefManager(this).getString("user_login")
        val user =  Gson().fromJson(json, User::class.java)
        if (user.id == responseDetailDiskusi.diskusi.makeBy.id){
            binding.btnDelete.visibility = View.VISIBLE
            binding.btnDelete.setOnClickListener {
                val builder = AlertDialog.Builder(this)
                    .setTitle("PERINGATAN")
                    .setMessage("Anda yakin ingin menghapus diskusi ini?")
                builder.setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
                    presenter.deleteDiskusi(user.id, responseDetailDiskusi.diskusi.id)
                })
                builder.setNegativeButton(
                    "Keluar",
                    DialogInterface.OnClickListener { dialog, which -> })
                builder.show()
            }
        }
        binding.rvKomentar.visibility = View.VISIBLE
        adapterKomentar.addList(responseDetailDiskusi.komentar)
        Log.d("dataKOmentar", responseDetailDiskusi.komentar.toString())
        val detail = responseDetailDiskusi.diskusi
        with(binding) {
            tvKategori.text = detail.timeline.name
            tvDeskripsi.text = detail.timeline.description
            tvPembuatTimeline.text = detail.timeline.makeBy.name
            tvPembuatDiskusi.text = detail.makeBy.name
            tvInviteDiskusi.text = detail.invite.name
            tvInviteDiskusi.text = detail.note
        }
        binding.btnAddComent.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.dialog_add_komentar, null)
            builder.setView(view)
            val komentar =  view.findViewById<EditText>(R.id.et_komentar)
            builder.setPositiveButton("Tambah", DialogInterface.OnClickListener { dialog, which ->
                presenter.addKomentar(komentar.text.toString(), responseDetailDiskusi.diskusi.id)

            })
            builder.setNegativeButton(
                "Keluar",
                DialogInterface.OnClickListener { dialog, which -> })
            builder.show()
        }


    }

    override fun addKomentar(responseKomentar: AddTimelineResponse) {
        val diskusi = intent.getStringExtra("idDiskusi")
        Toast.makeText(applicationContext, "Berhasil menambahkan komentar", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@DetailDiskusiActivity , DetailDiskusiActivity::class.java)
        intent.putExtra("idDiskusi", diskusi)
        startActivity(intent)
        finish()
    }

    override fun deleteDiskusi(deleteDiskusi: AddTimelineResponse) {
        Toast.makeText(applicationContext, "Berhasil menghapus diskusi", Toast.LENGTH_SHORT).show()
        startActivity(Intent(applicationContext, DashboardITActivity::class.java))
        finish()
    }

    override fun error(msg: String) {
        Toast.makeText(applicationContext, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
    }
}