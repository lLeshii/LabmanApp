package com.example.labmanv2.fragments.studlist

import MyViewModelFactory
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labmanv2.dialog.DelDialog
import com.example.labmanv2.R
import roomdb.Student
import roomdb.StudentAdapter
import roomdb.StudentGroupViewModel
import java.util.*
import kotlin.collections.ArrayList


class StudListFragment : Fragment(), StudentAdapter.ItemListener {

    private var studlist: RecyclerView? = null
    private var studSearch: SearchView? = null
    private lateinit var vm: StudentGroupViewModel
    private var stAdapter: StudentAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_studlist, container, false)
        studlist = view.findViewById(R.id.stud_list)
        studSearch = view.findViewById(R.id.search_stud)

        studSearch?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty())
                {
                    var nStList = ArrayList<Student?>()
                    val stList = vm.getAllStudentsNoLive()
                    stList?.forEach { stud->
                        if(stud!!.studentName!!.lowercase(Locale.getDefault()).contains(searchText)){
                            nStList.add(stud)
                        }
                    }
                    val adapter:StudentAdapter? = StudentAdapter(nStList as List<Student?>?)
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

        studlist?.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL,false)
        studlist?.adapter = stAdapter
        stAdapter?.setListener(this)

        vm =  ViewModelProvider(this, MyViewModelFactory(this.activity?.application as Application))[StudentGroupViewModel::class.java]
        vm.getAllStudents().observe(this.viewLifecycleOwner, Observer{ students ->
            stAdapter = StudentAdapter(students)
            studlist?.adapter = stAdapter
            stAdapter?.setListener(this)
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onItemClicked(student: Student, position: Int) {
        val dial = DelDialog(2,student.studentId)
        dial.show(requireActivity().supportFragmentManager,"hhhhhhhhhhh")
    }

    fun setListener(adapt: StudentAdapter?){
        adapt?.setListener(this)
    }


}