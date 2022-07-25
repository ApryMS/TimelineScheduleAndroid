package deploy.com.timelineschedule.ui.task

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import deploy.com.timelineschedule.databinding.ListTaskBinding
import deploy.com.timelineschedule.databinding.ListTimelineBinding
import deploy.com.timelineschedule.ui.home.TimelineItem
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*



class TaskAdapter (
    var task: ArrayList<TaskItem>,
    var listener: OnAdapterListener?
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    fun addList(list: List<TaskItem>){
        task.clear()
        task.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ListTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ListTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = task[position]
        with(holder.binding){
            if (task.status == "HOLD"){
                tvStatus.setTextColor(Color.parseColor("#FF0000"))
                view.setBackgroundColor(Color.parseColor("#FF0000"))
            }
            if (task.status == "FINISHED") {
                tvStatus.setTextColor(Color.parseColor("#1AAF20"))
                view.setBackgroundColor(Color.parseColor("#1AAF20"))
            }
            tvStatus.text = task.status
            tvJudul.text = task.name
            tvInvite.text = "Invited by" + " " + task.inviteBy.name
            val outputFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US)
            val date = inputFormat.parse(task.updatedAt)
            tvDate.text = outputFormat.format(date!!);
            tvStatusTask.text = task.status
            tvNameWorker.text = task.worker.name
        }
        holder.itemView.setOnClickListener {
            listener?.onClick(task)
        }
    }

    override fun getItemCount() = task.size

    interface  OnAdapterListener {
        fun onClick(timeline: TaskItem)
    }


}