package com.example.labmanv2.dialog

import MyViewModelFactory
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.labmanv2.R
import com.shawnlin.numberpicker.NumberPicker
import roomdb.Student
import roomdb.StudentGroupViewModel

class PickNumberDialog(var opType: Int, var stId: Int): DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view:View = inflater.inflate(R.layout.rewrite_dialog, container, false)

        var vm:StudentGroupViewModel =  ViewModelProvider(this,
            MyViewModelFactory(this.activity?.application as Application)
        )[StudentGroupViewModel::class.java]

        var lab_picker: NumberPicker = view.findViewById(R.id.pick_lab)
        var picker: NumberPicker = view.findViewById(R.id.pick_numb2)
        var title: TextView = view.findViewById(R.id.firstTitle)
        var butt: Button? = view.findViewById(R.id.pick_button5)

        lab_picker.minValue = 1

        when(opType){
            1 ->{
                lab_picker.maxValue = vm.getGroupByName(vm.getById(stId)!!.groupName!!)!!.labAmount!!
            }
            2->{
                lab_picker.maxValue = vm.getGroupByName(vm.getById(stId)!!.groupName!!)!!.testAmount!!
            }
            3->{
                lab_picker.maxValue = vm.getGroupByName(vm.getById(stId)!!.groupName!!)!!.cwAmount!!
            }
        }

        if(lab_picker.maxValue == 0){
            butt?.isEnabled = false
        }

        butt?.setOnClickListener {

            var lab_numb = lab_picker.value

            var st: Student? = vm.getById(stId)
            var arList: MutableList<String>? = null
            var size:Int = 0

            when(opType){
                1 ->{
                    arList = st?.labs?.toMutableList()
                    if(lab_numb > arList!!.size)
                    {
                        for(i in arList!!.size until lab_numb)
                        {
                            arList.add("")
                        }
                    }
                    arList!![lab_numb-1] = "${picker.value}"
                    st?.labs = arList!!.toList()

                }
                2->{
                    arList = st?.tests?.toMutableList()

                    if(lab_numb > arList!!.size)
                    {
                        for(i in arList!!.size until lab_numb)
                        {
                            arList.add("")
                        }
                    }
                    arList!![lab_numb-1] = "${picker.value}"
                    st?.tests = arList!!.toList()
                }
                3->{
                    arList = st?.cws?.toMutableList()
                    if(lab_numb > arList!!.size)
                    {
                        for(i in arList!!.size until lab_numb)
                        {
                            arList.add("")
                        }
                    }
                    arList!![lab_numb-1] = "${picker.value}"
                        st?.cws = arList!!.toList()
                }
            }
            vm.update(st!!)
            this.requireActivity().recreate()
            this.dismiss()
        }
        return view


    }
}
