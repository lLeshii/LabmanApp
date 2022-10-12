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
import roomdb.StudentGroupViewModel

class DelDialog(var type: Int, var itId: Int): DialogFragment()  {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.del_dialog, container, false)
        var delbutt: Button? = view.findViewById(R.id.del_button)
        var cancbutt: Button? = view.findViewById(R.id.cancel_butt)

        var vm: StudentGroupViewModel =  ViewModelProvider(this, MyViewModelFactory(this.activity?.application as Application))[StudentGroupViewModel::class.java]

        cancbutt?.setOnClickListener {
            this.dismiss()
        }

        delbutt?.setOnClickListener {
            when(type){
                1 ->{
                    Toast.makeText(context, "Deleted group: ${vm.getGroupById(itId)!!.groupName}", Toast.LENGTH_LONG).show()
                    vm.delete(vm.getGroupById(itId)!!)
                }
                2->{
                    vm.delete(vm.getById(itId)!!)
                }
                3->{
                    vm.delete(vm.getById(itId)!!)
                    this.requireActivity().recreate()
                }
            }
            this.dismiss()
        }
        return view

    }
}