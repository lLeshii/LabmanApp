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
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.labmanv2.R
import com.shawnlin.numberpicker.NumberPicker
import roomdb.Group
import roomdb.StudentGroupViewModel

class AddGroupDialog(): DialogFragment()  {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.add_group_dialog, container, false)
        var edText: EditText = view.findViewById(R.id.editTextGroupName)
        var lab_picker: NumberPicker = view.findViewById(R.id.pick_lab)
        var test_picker: NumberPicker = view.findViewById(R.id.pick_test)
        var cw_picker: NumberPicker = view.findViewById(R.id.pick_cw)
        var cr_butt: Button? = view.findViewById(R.id.create_button)
        var cancbutt: Button? = view.findViewById(R.id.canc_butt)

        cr_butt?.isEnabled = false

        var vm: StudentGroupViewModel =  ViewModelProvider(this, MyViewModelFactory(this.activity?.application as Application))[StudentGroupViewModel::class.java]

        cancbutt?.setOnClickListener {
            this.dismiss()
        }

        edText.addTextChangedListener(object : TextWatcher{
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
            var grDb:List<Group?>? = vm.getAllGroupsNoLive()
            var grStr:List<String> = GroupToString(grDb)
            if(grStr.contains(edText.text.toString()))
            {
                Toast.makeText(context, "Similar name: ${edText.text.toString()}", Toast.LENGTH_LONG).show()
            }
            else{
                vm.insert(Group(0,edText.text.toString(),lab_picker.value,test_picker.value,cw_picker.value))
            }
            this.dismiss()
        }
        return view
    }

    private fun GroupToString(list: List<Group?>?):List<String>{
        var grStr:ArrayList<String> = ArrayList<String>()
        for (i in 0 until list!!.size){
            grStr.add(list!![i]!!.groupName!!)
        }
        return grStr
    }
}