package com.example.labmanv2
import android.annotation.SuppressLint
import android.content.Intent
import roomdb.Student
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.example.labmanv2.R
import com.example.labmanv2.StudAcivity

class StudentGrAdapter(var students: List<Student?>?): RecyclerView.Adapter<StudentGrAdapter.StudentGrHolder>() {

    private lateinit var listener: StudentGrAdapter.ItemListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentGrHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.stgr_item, parent,
            false)
        return StudentGrHolder(itemView)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StudentGrHolder, position: Int) {
        var student:Student? = students!![position]
        holder.tvName.text = students!![position]?.studentName
        holder.tvLabs.text = "Labs: ${student!!.labs.filterNot {str->str == "" }.size}"
        holder.tvTests.text = "Tests: ${student!!.tests.filterNot {str->str == "" }.size}"
        holder.tvCws.text = "CWs: ${student!!.cws.filterNot {str->str == "" }.size}"
        holder.tvDelButton.setOnClickListener{ l->listener.onItemClicked(students!![position]!!, position) }
        holder.itemView?.setOnClickListener{ view->
            var studIntent:Intent = Intent(view.context, StudAcivity::class.java)
            studIntent.putExtra("studentId", students!![position]!!.studentId)
            view.context.startActivity(studIntent)
        }
    }


    inner class StudentGrHolder(iv: View) : RecyclerView.ViewHolder(iv) {

        val tvName: TextView = itemView.findViewById(R.id.stName)
        val tvLabs: TextView = itemView.findViewById(R.id.st_text_labs)
        val tvTests: TextView = itemView.findViewById(R.id.st_textTests)
        val tvCws: TextView = itemView.findViewById(R.id.st_textCW)
        val tvDelButton: Button = itemView.findViewById(R.id.stDelButton)
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