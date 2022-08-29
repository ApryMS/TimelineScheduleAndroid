package deploy.com.timelineschedule.ui.dashboard.diskusi.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import deploy.com.timelineschedule.databinding.ActivityDetailDiskusiBinding
import deploy.com.timelineschedule.network.ApiClient
import deploy.com.timelineschedule.preference.PrefManager

class DetailDiskusiActivity : AppCompatActivity(), ViewDiskusi {
    private val binding by lazy{ ActivityDetailDiskusiBinding.inflate(layoutInflater)}
    private lateinit var presenter: DiskusiPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = DiskusiPresenter(this, PrefManager(this), ApiClient.getService())
        val diskusi = intent.getStringExtra("idDiskusi")
        presenter.fetchDetailDiskusi(diskusi!!)
    }

    override fun loading(boolean: Boolean) {
        if (boolean) binding.pbDiskusi.visibility = View.VISIBLE else binding.pbDiskusi.visibility = View.GONE
    }

    override fun detailDiskusi(responseDetailDiskusi: ResponseDetailDiskusi) {
        val detail = responseDetailDiskusi.diskusi
        with(binding) {
            tvKategori.text = detail.timeline.name
            tvDeskripsi.text = detail.timeline.description
            tvPembuatTimeline.text = detail.timeline.makeBy.name
            tvPembuatDiskusi.text = detail.makeBy.name
            tvInviteDiskusi.text = detail.invite.name
            tvInviteDiskusi.text = detail.note
        }

    }

    override fun error(msg: String) {
        Toast.makeText(applicationContext, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
    }
}