package deploy.com.timelineschedule.ui.dashboard.diskusi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import deploy.com.timelineschedule.databinding.ListDiskusiBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DiskusiAdapter (
    var diskusiList: ArrayList<DiskusiItem>,
    var listener: OnAdapterListener?
    ) : RecyclerView.Adapter<DiskusiAdapter.ViewHolder>() {

    fun addList(list: List<DiskusiItem>){
        diskusiList.clear()
        diskusiList.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ListDiskusiBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ListDiskusiBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val diskusi = diskusiList[position]
        with(holder.binding){
            tvKategori.text = diskusi.timeline.name
            tvDiskusiInvite.text = "Invite" + " " + diskusi.invite.name
            tvDiskusiMake.text = "Dibuat" + " " + diskusi.makeBy.name
            tvNote.text = diskusi.note

            val outputFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US)
            val date = inputFormat.parse(diskusi.createdAt)
            tvDate.text = outputFormat.format(date!!);
        }
        holder.itemView.setOnClickListener {
            listener?.onClick(diskusi)
        }
    }

    override fun getItemCount() = diskusiList.size

    interface  OnAdapterListener {
        fun onClick(diskusi: DiskusiItem)
    }


}