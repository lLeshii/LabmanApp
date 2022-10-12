package com.example.labmanv2.dialog

import roomdb.Student

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
import roomdb.StudentGroupViewModel

class AddStudentDialog(var grId: Int): DialogFragment()  {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.add_student_dialog, container, false)
        var edText: EditText = view.findViewById(R.id.editTextStudName)
        var cr_butt: Button? = view.findViewById(R.id.add_button)
        var cancbutt: Button? = view.findViewById(R.id.cancel_butt2)
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
            this.requireActivity().recreate()
            vm.insert(Student(0,edText.text.toString(),vm.getGroupById(grId)!!.groupName,
                emptyList<String>(),emptyList<String>(),emptyList<String>(),0,0))
            Toast.makeText(context, "Student added", Toast.LENGTH_SHORT).show()
            this.requireActivity().recreate()
            this.dismiss()
        }
        return view
    }
}