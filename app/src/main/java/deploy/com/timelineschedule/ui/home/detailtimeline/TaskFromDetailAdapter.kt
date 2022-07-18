package deploy.com.timelineschedule.ui.home.detailtimeline

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import deploy.com.timelineschedule.databinding.ListTaskBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TaskFromDetailAdapter(
    var task: ArrayList<TaskItem>,
    var listener : OnAdapterListener?
) : RecyclerView.Adapter<TaskFromDetailAdapter.ViewHolder>() {

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
            tvJudul.text = task.name
            tvNameWorker.text = task.worker.name
            tvStatusTask.text = task.status

            val outputFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US)
            val date = inputFormat.parse(task.updatedAt)
            tvDate.text = outputFormat.format(date!!);
        }
    }

    override fun getItemCount() = task.size

    interface OnAdapterListener {
        fun onClick(task: TaskItem)
    }
}