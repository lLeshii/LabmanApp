package com.example.labmanv2.dialog

import MyViewModelFactory
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.labmanv2.R
import com.shawnlin.numberpicker.NumberPicker
import roomdb.Student
import roomdb.StudentGroupViewModel

class PickNumberDialog(var opType: Int, var stId: Int): DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view:View = inflater.inflate(R.layout.picker_dialog_custom, container, false)

        var picker: NumberPicker = view.findViewById(R.id.pick_numb)
        var butt: Button? = view.findViewById(R.id.pick_button)
        butt?.setOnClickListener {
            var vm:StudentGroupViewModel =  ViewModelProvider(this,
                MyViewModelFactory(this.activity?.application as Application)
            )[StudentGroupViewModel::class.java]
            var st: Student? = vm.getById(stId)
            var arList: MutableList<String>? = null
            var size:Int = 0
            when(opType){
                1 ->{
                    arList = st?.labs?.toMutableList()

                    if(arList!![0] == "")
                    {
                        size = arList!!.size.minus(1)
                    }
                    else{
                        size = arList!!.size
                    }

                    if(size < vm.getGroupByName(st!!.groupName!!)!!.labAmount!!)
                    {
                        arList?.add(picker.value.toString())
                        if(arList!![0] == "")
                        {
                            arList = arList.subList(1,arList.size)
                        }
                        st?.labs = arList!!.toList()
                    }
                    else
                    {
                        Toast.makeText(context, "Too much labs", Toast.LENGTH_LONG).show()
                        this.dismiss()
                    }
                }
                2->{
                    arList = st?.tests?.toMutableList()

                    if(arList!![0] == "")
                    {
                        size = arList!!.size.minus(1)
                    }
                    else{
                        size = arList!!.size
                    }

                    if(size < vm.getGroupByName(st!!.groupName!!)!!.testAmount!!)
                    {
                        arList?.add(picker.value.toString())
                        if(arList!![0] == "")
                        {
                            arList = arList.subList(1,arList.size)
                        }
                        st?.tests = arList!!.toList()
                    }
                    else
                    {
                        Toast.makeText(context, "Too much tests", Toast.LENGTH_LONG).show()
                        this.dismiss()
                    }
                }
                3->{
                    arList = st?.cws?.toMutableList()

                    if(arList!![0] == "")
                    {
                        size = arList!!.size.minus(1)
                    }
                    else{
                        size = arList!!.size
                    }

                    if(size < vm.getGroupByName(st!!.groupName!!)!!.cwAmount!!)
                    {
                        arList?.add(picker.value.toString())
                        if(arList!![0] == "")
                        {
                            arList = arList.subList(1,arList.size)
                        }
                        st?.cws = arList!!.toList()
                    }
                    else
                    {
                        Toast.makeText(context, "Too much cws", Toast.LENGTH_LONG).show()
                        this.dismiss()
                    }
                }
            }
            vm.update(st!!)
            this.requireActivity().recreate()
            this.dismiss()
        }
        return view


    }
}
