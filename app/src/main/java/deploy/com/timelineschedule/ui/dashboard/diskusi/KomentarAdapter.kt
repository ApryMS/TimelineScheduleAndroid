package deploy.com.timelineschedule.ui.dashboard.diskusi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import deploy.com.timelineschedule.databinding.ListKomentarBinding
import deploy.com.timelineschedule.ui.dashboard.diskusi.detail.KomentarItem
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class KomentarAdapter (
    var komentar: ArrayList<KomentarItem>,
    var listener: OnAdapterListener?
    ) : RecyclerView.Adapter<KomentarAdapter.ViewHolder>() {

    fun addList(list: List<KomentarItem>){
        komentar.clear()
        komentar.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ListKomentarBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ListKomentarBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val komentar = komentar[position]
        with(holder.binding){
            tvNameKomentar.text = komentar.userId.name
            tvKomentar.text = komentar.komen

            val outputFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US)
            val date = inputFormat.parse(komentar.createdAt)
            tvDate.text = outputFormat.format(date!!);
        }
        holder.itemView.setOnClickListener {
            listener?.onClick(komentar)
        }
    }

    override fun getItemCount() = komentar.size

    interface  OnAdapterListener {
        fun onClick(komentar: KomentarItem)
    }


}