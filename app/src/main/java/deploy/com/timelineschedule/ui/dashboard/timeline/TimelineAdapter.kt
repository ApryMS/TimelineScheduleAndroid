package deploy.com.timelineschedule.ui.dashboard.timeline

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import deploy.com.timelineschedule.databinding.ListTimelineBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class TimelineAdapter (
    var timeline: ArrayList<TimelineItem>,
    var listener: OnAdapterListener?
    ) : RecyclerView.Adapter<TimelineAdapter.ViewHolder>() {

    fun addList(list: List<TimelineItem>){
        timeline.clear()
        timeline.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ListTimelineBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ListTimelineBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val timeline = timeline[position]
        with(holder.binding){
            txtJudul.text = timeline.name
            txtInvite.text = "Invite" + " " + timeline.invite.name
            txtDescription.text = timeline.description
            tvStatus.text = timeline.statusTask

            if (timeline.status == "HOLD"){
                tvStatus.setTextColor(Color.parseColor("#FF0000"))
                view.setBackgroundColor(Color.parseColor("#FF0000"))
            }
            if (timeline.statusTask == "FINISHED"){
                tvStatus.setTextColor(Color.parseColor("#FFFF9800"))
                view.setBackgroundColor(Color.parseColor("#FFFF9800"))
            }
            if (timeline.status == "FINISHED") {
                tvStatus.setTextColor(Color.parseColor("#1AAF20"))
                view.setBackgroundColor(Color.parseColor("#1AAF20"))
            }
            val outputFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US)
            val date = inputFormat.parse(timeline.updatedAt)
            tvDate.text = outputFormat.format(date!!);
        }
        holder.itemView.setOnClickListener {
            listener?.onClick(timeline)
        }
    }

    override fun getItemCount() = timeline.size

    interface  OnAdapterListener {
        fun onClick(timeline: TimelineItem)
    }


}