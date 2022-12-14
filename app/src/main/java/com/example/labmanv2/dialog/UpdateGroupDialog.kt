package com.example.labmanv2.dialog

import MyViewModelFactory
import android.app.Application
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.labmanv2.R
import com.shawnlin.numberpicker.NumberPicker
import roomdb.Group
import roomdb.StudentGroupViewModel

class UpdateGroupDialog(var grId: Int): DialogFragment()  {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.add_group_dialog, container, false)
        var vm: StudentGroupViewModel =  ViewModelProvider(this,
            MyViewModelFactory(this.activity?.application as Application)
        )[StudentGroupViewModel::class.java]
        var lab_picker: NumberPicker = view.findViewById(R.id.pick_lab)
        var test_picker: NumberPicker = view.findViewById(R.id.pick_test)
        var cw_picker: NumberPicker = view.findViewById(R.id.pick_cw)
        var edText: EditText = view.findViewById(R.id.editTextGroupName)
        var text: TextView = view.findViewById(R.id.firstTitle)
        var cr_butt: Button? = view.findViewById(R.id.create_button)
        var cancbutt: Button? = view.findViewById(R.id.canc_butt)
        var group:Group? = vm.getGroupById(grId)
        edText.setText(group!!.groupName!!)
        lab_picker.value = group.labAmount!!
        test_picker.value = group.testAmount!!
        cw_picker.value = group.cwAmount!!
        cr_butt?.text = "Update"
        text.text = "Editing"
        cancbutt?.setOnClickListener {
            this.dismiss()
        }

        edText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                cr_butt?.isEnabled = edText.text.toString() != ""
            }
        })

        cr_butt?.setOnClickListener {
//            this.requireActivity().recreate()
            vm.update(Group(grId,edText.text.toString(),lab_picker.value,test_picker.value,cw_picker.value))
            update_all(lab_picker.value, test_picker.value, cw_picker.value, vm)
            this.dismiss()
        }
        return view
    }

    private fun update_all(l:Int, t: Int, cw: Int, vm :StudentGroupViewModel){
        var studs = vm.getStudentsFromGroup(vm.getGroupById(grId)!!.groupName!!)
        for(i in 0 until studs!!.size)
        {
            var arList: MutableList<String>? = null
            if(studs[i]!!.labs.size > l){
                arList = studs[i]?.labs?.toMutableList()
                for(j in l  until studs[i]!!.labs.size)
                {
                    arList!!.removeAt(l)
                }
                studs[i]!!.labs = arList as List<String>
            }

            if(studs[i]!!.tests.size > t){
                arList = studs[i]?.tests?.toMutableList()
                for(j in t  until studs[i]!!.tests.size)
                {
                    arList!!.removeAt(t)
                }
                studs[i]!!.tests = arList as List<String>
            }

            if(studs[i]!!.cws.size > cw){
                arList = studs[i]?.cws?.toMutableList()
                for(j in cw  until studs[i]!!.cws.size)
                {
                    arList!!.removeAt(cw)
                }
                studs[i]!!.cws = arList as List<String>
            }
            vm.update(studs[i]!!)
        }
    }
}