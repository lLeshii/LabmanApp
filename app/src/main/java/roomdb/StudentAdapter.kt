package roomdb
import android.content.Intent
import roomdb.Student
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.example.labmanv2.R
import com.example.labmanv2.StudAcivity

class StudentAdapter(var students: List<Student?>?): RecyclerView.Adapter<StudentAdapter.StudentHolder>() {

    private lateinit var listener: StudentAdapter.ItemListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.student_item, parent,
            false)
        return StudentHolder(itemView)
    }

    override fun onBindViewHolder(holder: StudentHolder, position: Int) {
        holder.tvName.text = students!![position]?.studentName
        holder.tvGrName.text = students!![position]?.groupName
        holder.tvDelButton.setOnClickListener{ l->listener.onItemClicked(students!![position]!!, position) }
        holder.itemView?.setOnClickListener{ view->
            var studIntent:Intent = Intent(view.context, StudAcivity::class.java)
            studIntent.putExtra("studentId", students!![position]!!.studentId)
            view.context.startActivity(studIntent)
        }
    }

    inner class StudentHolder(iv: View) : RecyclerView.ViewHolder(iv) {

        val tvName: TextView = itemView.findViewById(R.id.text_view_name)
        val tvGrName: TextView = itemView.findViewById(R.id.text_view_grname)
        val tvDelButton: Button = itemView.findViewById(R.id.studDelButton)
    }

    fun setListener(listener: ItemListener) {
        this.listener = listener
    }

    interface ItemListener {
        fun onItemClicked(student: Student, position: Int)
    }

    override fun getItemCount(): Int {
        return students!!.size
    }
}