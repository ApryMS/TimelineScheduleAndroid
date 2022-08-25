package deploy.com.timelineschedule.ui.home.detailtimeline


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.google.gson.Gson
import deploy.com.timelineschedule.BaseActivity
import deploy.com.timelineschedule.R
import deploy.com.timelineschedule.databinding.ActivityDetailBinding
import deploy.com.timelineschedule.network.ApiClient
import deploy.com.timelineschedule.preference.PrefManager
import deploy.com.timelineschedule.ui.dashboard.DashboardActivity
import deploy.com.timelineschedule.ui.dashboard.DashboardITActivity
import deploy.com.timelineschedule.ui.dashboard.timeline.UpdateTimelineResponse
import deploy.com.timelineschedule.ui.home.changeinvitation.ChangeInvitationActivity
import deploy.com.timelineschedule.ui.login.User
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : BaseActivity() , DetailViewTImeline {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private lateinit var presenter: DetailTimelinePresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = DetailTimelinePresenter(this, ApiClient.getService(), PrefManager(this))
        val id = intent.getStringExtra("idTimeline")!!
        presenter.fetchDetailTimeline(id)


    }

    override fun loading(boolean: Boolean) {
        if (boolean) {
            with(binding){
                pbLoading.visibility = View.VISIBLE
                btnFinishedTimeline.visibility= View.GONE
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

        binding.btnFinishedTimeline.visibility = View.VISIBLE
        val json = PrefManager(this).getString("user_login")
        val user =  Gson().fromJson(json, User::class.java)
        val outputFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US)
        val url = "http://45.249.216.57:3000/"
        val image = response.timeline.image.replace("""\""", "/")
        val urlImage = url+image
        var requestOptions = RequestOptions()
        requestOptions.signature(ObjectKey(System.currentTimeMillis()))
        Glide.with(applicationContext)
            .load(urlImage)
            .placeholder(R.drawable.ic_broken_image_48)
            .apply(requestOptions)
            .into(binding.imageViewTL)

        binding.tvNamePengaju.text = response.timeline.makeBy.name
        binding.tvTokoDetail.text = response.timeline.makeBy.toko

        if (response.timeline.statusTask == "FINISHED"){
            binding.textView10.visibility = View.VISIBLE
            binding.tvTglSelesai.visibility = View.VISIBLE
            val dateFinish = inputFormat.parse(response.timeline.updatedAt)!!
            binding.tvTglSelesai.text = outputFormat.format(dateFinish)
        }
        if (response.timeline.status == "HOLD"){
            binding.btnFinishedTimeline.isEnabled = true
            binding.textView10.visibility = View.GONE
            binding.tvTglSelesai.visibility = View.GONE
        } else {
            binding.btnFinishedTimeline.isEnabled = false
            binding.textView10.visibility = View.VISIBLE
            binding.tvTglSelesai.visibility = View.VISIBLE
            val dateFinish = inputFormat.parse(response.timeline.updatedAt)!!
            binding.tvTglSelesai.text = outputFormat.format(dateFinish)

        }
        binding.button.setOnClickListener {
            val intent = Intent(this@DetailActivity, ChangeInvitationActivity::class.java)
            intent.putExtra("idTimeline", response.timeline.id)
            startActivity(intent)

        }
        binding.btnFinishedTimeline.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            if (response.timeline.invite.id == user.id){

                val view = layoutInflater.inflate(R.layout.dialog_confirm, null)
                builder.setView(view)
                builder.setPositiveButton("Sudah", DialogInterface.OnClickListener { dialog, which ->
                    presenter.updateStatusTaskTimeline(response.timeline.id)
                })
                builder.setNegativeButton(
                    "Belum",
                    DialogInterface.OnClickListener { dialog, which -> })
                builder.show()
            } else {
                val view = layoutInflater.inflate(R.layout.dialog_confirm_timeline, null)
                builder.setView(view)
                builder.setPositiveButton("Sudah", DialogInterface.OnClickListener { dialog, which ->
                    presenter.updateTimeline(response.timeline.id)


                })
                builder.setNegativeButton(
                    "Belum",
                    DialogInterface.OnClickListener { dialog, which -> })
                builder.show()
            }

        }

        if (user.id == response.timeline.invite.id) {
            if (response.timeline.statusTask == "FINISHED") {
                binding.button.visibility = View.GONE
                binding.warning.visibility = View.GONE
                binding.btnFinishedTimeline.isEnabled = false
                binding.btnFinishedTimeline.text = "Selesai"
            } else {
                binding.button.visibility = View.VISIBLE
                binding.btnFinishedTimeline.text = "Sudah Selesai ?"
            }



        } else{
            binding.button.visibility = View.GONE
            binding.warning.visibility = View.GONE
            if (response.timeline.makeBy.id == user.id && response.timeline.statusTask == "HOLD") {
                binding.btnFinishedTimeline.isEnabled = false
            }

        }
        binding.imageViewTL.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.image_popup, null)
            builder.setView(view)
            val imageView =  view.findViewById<ImageView>(R.id.iv_popup)
            Glide.with(applicationContext)
                .load(urlImage)
                .placeholder(R.drawable.ic_broken_image_48)
                .into(imageView)
            builder.show()
        }

        val date = inputFormat.parse(response.timeline.createdAt)!!


        with(binding){
            tvName.text = response.timeline.name
            tvCreated.text = outputFormat.format(date)
            tvDescroptiom.text = response.timeline.description
            tvStatus.text = response.timeline.statusTask
            tvNameInvite.text = response.timeline.invite.name
        }

    }

    override fun updateTimelineResponse(responseUpdate: UpdateTimelineResponse) {
        val json = PrefManager(this).getString("user_login")
        val user =  Gson().fromJson(json, User::class.java)
        Toast.makeText(applicationContext, responseUpdate.message, Toast.LENGTH_SHORT).show()
        if (user.position == "KARYAWAN") {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, DashboardITActivity::class.java))
            finish()
        }

    }

    override fun error(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}