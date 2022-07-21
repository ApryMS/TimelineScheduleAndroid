package deploy.com.timelineschedule.ui.home.addtimeline


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import deploy.com.timelineschedule.databinding.ItemInviteBinding


class InviteAdapter (
    var invite : ArrayList<EmployeeInvite>,
    var listener: OnAdapterListener?
        ) : RecyclerView.Adapter<InviteAdapter.ViewHolder>() {

    fun addList(list: List<EmployeeInvite>){
        invite.clear()
        invite.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemInviteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemInviteBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employe = invite[position]
        holder.binding.inviteEmployee.text = employe.name
    }

    override fun getItemCount() = invite.size

    interface OnAdapterListener {
        fun onClick(employe: EmployeeInvite)
    }

}