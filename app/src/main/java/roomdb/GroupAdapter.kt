package roomdb

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.labmanv2.R
import com.example.labmanv2.StudentsGrActivity

class GroupAdapter(var groups: List<Group?>?) : RecyclerView.Adapter<GroupAdapter.GroupHolder>() {

    private lateinit var listener: ItemListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.group_item, parent, false)
        return GroupHolder(itemView)
    }

    override fun onBindViewHolder(holder: GroupHolder, position: Int) {
        holder.tvGrName.text = groups!![position]?.groupName
        holder.tvLabs.text = "Labs: ${groups!![position]?.labAmount}"
        holder.tvTests.text = "Tests: ${groups!![position]?.testAmount}"
        holder.tvCws.text = "CWs: ${groups!![position]?.cwAmount}"
        holder.tvDel.setOnClickListener{ l->listener.onItemClicked(groups!![position]!!, position) }
        holder.itemView.setOnClickListener{
            var studIntent: Intent = Intent(it.context, StudentsGrActivity::class.java)
            studIntent.putExtra("groupId", groups!![position]!!.groupId)
            it.context.startActivity(studIntent)
        }

        holder.itemView.setOnLongClickListener{l->
            listener.OnItemLClicked(groups!![position]!!, position)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return groups!!.size
    }

    inner class GroupHolder(iv: View) : RecyclerView.ViewHolder(iv) {
        val tvGrName: TextView = itemView.findViewById(R.id.textGrName)
        val tvLabs: TextView = itemView.findViewById(R.id.text_labs)
        val tvTests: TextView = itemView.findViewById(R.id.textTests)
        val tvCws: TextView = itemView.findViewById(R.id.textCW)
        val tvDel:Button = itemView.findViewById(R.id.grDelButton)
    }

    fun setListener(listener: ItemListener) {
        this.listener = listener
    }

    interface ItemListener {
        fun onItemClicked(group: Group, position: Int)
        fun OnItemLClicked(group: Group, position: Int)
    }
}
