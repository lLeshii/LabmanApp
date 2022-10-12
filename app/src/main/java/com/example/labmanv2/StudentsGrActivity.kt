package com.example.labmanv2

import MyViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labmanv2.dialog.AddStudentDialog
import com.example.labmanv2.dialog.DelDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import roomdb.Group
import roomdb.Student
import roomdb.StudentGroupViewModel
import java.util.*
import kotlin.collections.ArrayList

class StudentsGrActivity : AppCompatActivity(), StudentGrAdapter.ItemListener {

    private var studlist: RecyclerView? = null
    private var butt: FloatingActionButton? = null
    private var studSearch: SearchView? = null
    private var title: TextView? = null
    private lateinit var vm: StudentGroupViewModel
    private var stAdapter: StudentGrAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students_gr)
        title = this.findViewById(R.id.group_name)
        studlist = this.findViewById(R.id.studgr_list)
        studSearch = this.findViewById(R.id.search_stud_gr)
        butt = this.findViewById(R.id.floatingActionButtonGr)

        val groupId = intent.getIntExtra("groupId", 0)
        vm =  ViewModelProvider(this, MyViewModelFactory(this.application))[StudentGroupViewModel::class.java]
        var group: Group? = vm.getGroupById(groupId)
        title!!.text = "Group:  ${group!!.groupName}"
        stAdapter = StudentGrAdapter(vm.getStudentsFromGroup(group?.groupName!!))

        butt?.setOnClickListener{
            val dial = AddStudentDialog(groupId)
            dial.show(supportFragmentManager,"hhhhhhhhhhh")
        }

        studSearch?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty())
                {
                    var nStList = ArrayList<Student?>()
                    val stList = vm.getStudentsFromGroup(group?.groupName!!)
                    stList?.forEach { stud->
                        if(stud!!.studentName!!.lowercase(Locale.getDefault()).contains(searchText)){
                            nStList.add(stud)
                        }
                    }
                    val adapter:StudentGrAdapter? = StudentGrAdapter(nStList as List<Student?>?)
                    studlist?.adapter = adapter
                    setListener(adapter)

                }else{
                    studlist?.adapter = stAdapter
                    setListener(stAdapter)
                }
                return false
            }

        })

        studSearch?.setOnClickListener{
            studSearch?.isIconified = false
        }

        studlist?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        studlist?.adapter = stAdapter
        stAdapter?.setListener(this)



    }

    override fun onItemClicked(student: Student, position: Int) {
        val dial = DelDialog(3,student.studentId)
        dial.show(supportFragmentManager,"hhhhhhhhhhh")
    }


    fun setListener(adapt: StudentGrAdapter?){
        adapt?.setListener(this)
    }

}