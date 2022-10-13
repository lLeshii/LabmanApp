package com.example.labmanv2

import MyViewModelFactory
import com.example.labmanv2.dialog.PickNumberDialog
import android.annotation.SuppressLint
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.labmanv2.dialog.ListNumbDialog
import roomdb.StudentGroupViewModel

class StudAcivity() : AppCompatActivity() {
    private lateinit var vm: StudentGroupViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
        setContentView(R.layout.stud_acivity)

        vm =  ViewModelProvider(this, MyViewModelFactory(this.application as Application))[StudentGroupViewModel::class.java]

        val studentId = intent.getIntExtra("studentId", 0)
        val student = vm.getById(studentId)
        val group = vm.getGroupByName(student?.groupName!!)

        var nameText: TextView? = this.findViewById(R.id.name_text)
        var groupText: TextView? = this.findViewById(R.id.group_text)
        var labText: TextView? = this.findViewById(R.id.lab_text)
        var testText: TextView? = this.findViewById(R.id.test_text)
        var cwText: TextView? = this.findViewById(R.id.cw_text)
        var plusText: TextView? = this.findViewById(R.id.plus_text)
        var minText: TextView? = this.findViewById(R.id.min_text)

        var labButt: Button? = this.findViewById(R.id.lab_button)
        var testButt: Button? = this.findViewById(R.id.test_button)
        var cwButt: Button? = this.findViewById(R.id.cw_button)
        var plusButt: Button? = this.findViewById(R.id.plus_button)
        var minButt: Button? = this.findViewById(R.id.min_button)

        nameText?.text = "${student.studentName}"
        groupText?.text = "Group: ${student.groupName}"
        labText?.text = "Labs: ${student.labs.filterNot {str->str == "" }.size}/${group?.labAmount}"
        testText?.text = "Tests: ${student.tests.filterNot {str->str == "" }.size}/${group?.testAmount}"
        cwText?.text = "CWs: ${student.cws.filterNot {str->str == "" }.size}/${group?.cwAmount}"
        plusText?.text = "+:     ${student.pluses}"
        minText?.text = "-:     ${student.minuses}"

        labButt?.setOnClickListener {
            val dial = PickNumberDialog(1,studentId)
            dial.show(supportFragmentManager,"hhhhhhhhhhh")
        }

        labButt?.setOnLongClickListener{
            val dial = ListNumbDialog(1,studentId)
            dial.show(supportFragmentManager,"hhhhhhhhhhh")
            return@setOnLongClickListener true
        }
        testButt?.setOnClickListener {
            val dial = PickNumberDialog(2,studentId)
            dial.show(supportFragmentManager,"hhhhhhhhhhh")
        }
        testButt?.setOnLongClickListener{
            val dial = ListNumbDialog(2,studentId)
            dial.show(supportFragmentManager,"hhhhhhhhhhh")
            return@setOnLongClickListener true
        }
        cwButt?.setOnClickListener {
            val dial = PickNumberDialog(3,studentId)
            dial.show(supportFragmentManager,"hhhhhhhhhhh")
        }
        cwButt?.setOnLongClickListener{
            val dial = ListNumbDialog(3,studentId)
            dial.show(supportFragmentManager,"hhhhhhhhhhh")
            return@setOnLongClickListener true
        }
        plusButt?.setOnClickListener {
            student.pluses = student.pluses?.plus(1)
            vm.update(student!!)
            plusText?.text="+:     ${student.pluses}"
        }
        plusButt?.setOnLongClickListener{
            if(student.pluses!! > student.minuses!!){
                student.pluses = student.pluses!!.minus(student.minuses!!)
                student.minuses = 0
            }
            else{
                student.minuses = student.minuses!!.minus(student.pluses!!)
                student.pluses = 0
            }
            vm.update(student!!)
            plusText?.text="+:     ${student.pluses}"
            minText?.text="-:     ${student.minuses}"
            return@setOnLongClickListener true
        }
        minButt?.setOnClickListener {
            student.minuses = student.minuses?.plus(1)
            vm.update(student!!)
            minText?.text="-:     ${student.minuses}"
        }
        minButt?.setOnLongClickListener{
            student.minuses = 0
            student.pluses = 0
            vm.update(student!!)
            plusText?.text="+:     ${student.pluses}"
            minText?.text="-:     ${student.minuses}"
            return@setOnLongClickListener true
        }
    }

}