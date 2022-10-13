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
import com.shawnlin.numberpicker.NumberPicker
import roomdb.Student
import roomdb.StudentGroupViewModel

class RewriteDialog(var type: Int, var stId: Int): DialogFragment()  {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.rewrite_dialog, container, false)
        var title: TextView = view.findViewById(R.id.firstTitle)
        var title2: TextView = view.findViewById(R.id.secTitle)
        var butt: Button? = view.findViewById(R.id.pick_button5)
        var picker1: NumberPicker = view.findViewById(R.id.pick_lab)
        var picker2: NumberPicker = view.findViewById(R.id.pick_numb2)

        var vm: StudentGroupViewModel =  ViewModelProvider(this, MyViewModelFactory(this.activity?.application as Application))[StudentGroupViewModel::class.java]
        var st: Student? = vm.getById(stId)

        var arList: MutableList<String>? = null
        when(type){
            1 ->{
                arList = st?.labs?.toMutableList()
                title.text="Choose Lab:"
            }
            2->{
                arList = st?.tests?.toMutableList()
                title.text="Choose Test:"
            }
            3->{
                arList = st?.cws?.toMutableList()
                title.text="Choose CW:"
            }
        }
        picker1.minValue = 1
        picker1.value = 2
        picker1.maxValue = arList!!.size

        butt?.setOnClickListener {
            arList[picker1.value.minus(1)] = picker2.value.toString()
            when(type){
                1 ->{
                   st?.labs = arList as List<String>
                }
                2->{
                    st?.tests = arList as List<String>
                }
                3->{
                    st?.cws = arList as List<String>
                }
            }
            vm.update(st!!)
            this.dismiss()
        }
        return view
    }
}