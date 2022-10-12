package com.example.labmanv2.dialog

import MyViewModelFactory
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.labmanv2.R
import roomdb.Student
import roomdb.StudentGroupViewModel

class ListNumbDialog(var type: Int, var stId: Int): DialogFragment()  {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.dialog_fragment_custom, container, false)
        var title: TextView = view.findViewById(R.id.textViewList)
        var picker: TextView = view.findViewById(R.id.listText)
        var buttrew: Button? = view.findViewById(R.id.change_button)
        var butt: Button? = view.findViewById(R.id.pick_button2)

        var vm: StudentGroupViewModel =  ViewModelProvider(this, MyViewModelFactory(this.activity?.application as Application))[StudentGroupViewModel::class.java]
        var st: Student? = vm.getById(stId)
        var arList: MutableList<String>? = null
        when(type){
            1 ->{
                arList = st?.labs?.toMutableList()
                title.text="Labs:"
            }
            2->{
                arList = st?.tests?.toMutableList()
                title.text="Tests:"
            }
            3->{
                arList = st?.cws?.toMutableList()
                title.text="CWs:"
            }
        }
        if(arList!![0]!! ==""){
            picker.text = "¯\\_(ツ)_/¯"
            buttrew?.isEnabled = false
        }
        else{
            picker.text = arList.toString()
        }

        butt?.setOnClickListener {
            this.dismiss()
        }

        buttrew?.setOnClickListener {
            when(type){
                1 ->{
                    val dial = RewriteDialog(1,stId)
                    dial.show(this!!.requireActivity().supportFragmentManager,"hhhhhhhhhhh")
                }
                2->{
                    val dial = RewriteDialog(2,stId)
                    dial.show(this!!.requireActivity().supportFragmentManager,"hhhhhhhhhhh")
                }
                3->{
                    val dial = RewriteDialog(3,stId)
                    dial.show(this!!.requireActivity().supportFragmentManager,"hhhhhhhhhhh")
                }
            }
            this.dismiss()
        }
        return view

    }
}