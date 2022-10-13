package com.example.labmanv2.fragments.grouplist

import MyViewModelFactory
import com.example.labmanv2.dialog.UpdateGroupDialog
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider.getUriForFile
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labmanv2.*
import com.example.labmanv2.dialog.AddGroupDialog
import com.example.labmanv2.dialog.DelDialog
import com.example.labmanv2.dialog.ImpExpDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import roomdb.*
import java.io.File
import java.io.InputStream
import java.io.OutputStream


class  GroupListFragment: Fragment(), GroupAdapter.ItemListener, ImpExpDialog.ImpExpListener {

    private var file_butt: FloatingActionButton? = null
    private var grouplist: RecyclerView? = null
    private lateinit var vm: StudentGroupViewModel
    private var grAdapter: GroupAdapter? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        try {
            val inputStream: InputStream? = requireActivity().contentResolver.openInputStream(uri!!)
            inputStream.use { stream ->
                val text = stream?.bufferedReader().use {
                    it?.readText()
                }
                jsonDataToDB(text.toString())
            }
        }
        catch(e: Exception) {
            Toast.makeText(context, "Wrong file", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_grouplist, container, false)
        file_butt = view.findViewById(R.id.floatingActionButton)
        grouplist = view.findViewById(R.id.group_list)

        file_butt?.setOnClickListener {
            val dial = AddGroupDialog()
            dial.show(requireActivity().supportFragmentManager,"hhhhhhhhhhh")
        }

        file_butt?.setOnLongClickListener {
            val dial = ImpExpDialog()
            dial.setListener(this)
            dial.show(requireActivity().supportFragmentManager,"hhhhhhhhhhh")
            return@setOnLongClickListener true
        }

        grouplist?.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL,false)
        grouplist?.adapter = grAdapter
        grAdapter?.setListener(this)

        vm =  ViewModelProvider(this, MyViewModelFactory(this.activity?.application as Application))[StudentGroupViewModel::class.java]
        vm.getAllGroups().observe(this.viewLifecycleOwner, Observer{groups->
            grAdapter = GroupAdapter(groups)
            grouplist?.adapter = grAdapter
            grAdapter?.setListener(this)
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun jsonDataToDB(text: String?) {
        var groups = JSONObject(text)["groups"] as JSONArray

        var grDb:List<Group?>? = vm.getAllGroupsNoLive()
        var grStr:List<String> = GroupToString(grDb)
        for (i in 0 until groups.length()) {

            var proto = groups.getJSONObject(i)
            var group = proto.get("group") as String
            var labs = proto.get("lab_amount") as Int
            var tests = proto.get("tests") as Int
            var cws = proto.get("control_works") as Int
            if(grStr.contains(group))
            {
                Toast.makeText(context, "Similar name: ${group}", Toast.LENGTH_LONG).show()
                continue
            }
            vm.insert(Group(0, group, labs, tests, cws))
            var students = proto.get("students") as JSONArray

            for (j in 0 until students.length()) {
                var name = students.get(j) as String
                vm.insert(Student( 0,name, group, emptyList<String>(), emptyList<String>(), emptyList<String>(), 0, 0))
            }
        }

    }

    private fun DBtoJSON(): String {
        var grDb:List<Group?>? = vm.getAllGroupsNoLive()
        var massDat = ArrayList<GroupExp>()
        for (i in 0 until grDb!!.size) {
            massDat.add(GroupExp(grDb[i]!!.groupName, grDb[i]!!.labAmount, grDb[i]!!.testAmount, grDb[i]!!.cwAmount, vm.getStudentsFromGroup(grDb[i]!!.groupName!!)!!.toTypedArray()))
        }
        var groups = Gson().toJson(massDat.toTypedArray())
        return groups
    }

    override fun onItemClicked(group: Group, position: Int) {
        lifecycleScope.launch {
            val dial = DelDialog(1,group.groupId)
            dial.show(requireActivity().supportFragmentManager,"hhhhhhhhhhh")
        }
    }

    override fun OnItemLClicked(group: Group, position: Int) {
        lifecycleScope.launch {
            val dial = UpdateGroupDialog(group.groupId)
            dial.show(requireActivity().supportFragmentManager,"hhhhhhhhhhh")
        }
    }

    private fun GroupToString(list: List<Group?>?):List<String>{
        var grStr:ArrayList<String> = ArrayList<String>()
        for (i in 0 until list!!.size){
            grStr.add(list!![i]!!.groupName!!)
        }
        return grStr
    }

    override fun onImp() {
        getContent.launch("*/*")
    }

    override fun OnExpJ() {
        var jsArr: String = DBtoJSON()
        val imagePath: File = File(requireContext().filesDir, "/")
        var file:File = File("/data/data/com.example.labmanv2/files","Students.json")
        val contentUri = getUriForFile(requireContext(), "com.example.labmanv2.provider", file)

        val outputStream: OutputStream? = requireActivity().contentResolver.openOutputStream(contentUri!!)
        outputStream.use { stream ->
            stream?.bufferedWriter().use {
                it?.write(jsArr)
            }
        }
        var shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/*"
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
        startActivity(Intent.createChooser(shareIntent, "Send file via "))
    }

    override fun OnExpE() {
        var csvArr: String = dbToCsv()
        val imagePath: File = File(requireContext().filesDir, "/")
        var file:File = File("/data/data/com.example.labmanv2/files","Students.csv")
        val contentUri = getUriForFile(requireContext(), "com.example.labmanv2.provider", file)

        val outputStream: OutputStream? = requireActivity().contentResolver.openOutputStream(contentUri!!)
        outputStream.use { stream ->
            stream?.bufferedWriter().use {
                it?.write(csvArr)
            }
        }
        var shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/*"
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
        startActivity(Intent.createChooser(shareIntent, "Send file via "))
    }

    private fun dbToCsv(): String{
        var startString = "Group,Name"
        var lab_amount = 0
        var test_amount = 0
        var cw_amount = 0

        var groups: List<Group?> = vm.getAllGroupsNoLive()!!

        for(i in 0 until groups.size)
        {
            if(groups[i]!!.labAmount!! > lab_amount){
                lab_amount = groups[i]!!.labAmount!!
            }
            if(groups[i]!!.testAmount!! > test_amount){
                test_amount = groups[i]!!.testAmount!!
            }
            if(groups[i]!!.cwAmount!! > cw_amount){
                cw_amount = groups[i]!!.cwAmount!!
            }
        }

        startString = addTableColumn("Lab", lab_amount, startString)
        startString = addTableColumn("Test", test_amount, startString)
        startString = addTableColumn("CW", cw_amount, startString)
        startString += ",Plus,Minus"
        var students: List<Student?> = vm.getAllStudentsNoLive()!!

        for(i in 0 until students.size)
        {
            var stString = "\n"
            stString+="${students[i]?.groupName},${students[i]!!.studentName}"
            var student = students[i]

            if(student!!.labs.size < lab_amount ){
                for(j in 0 until student.labs.size) {
                    stString+=",${student.labs[j]}"
                }
                for(j in student.labs.size until lab_amount) {
                    stString+=","
                }
            }
            else{
                for(j in 0 until lab_amount) {
                    stString+=",${student.labs[j]}"
                }
            }

            if(student.tests.size < test_amount ){
                for(j in 0 until student.tests.size) {
                    stString+=",${student.tests[j]}"
                }
                for(j in student.tests.size until test_amount) {
                    stString+=","
                }
            }
            else{
                for(j in 0 until test_amount) {
                    stString+=",${student.tests[j]}"
                }
            }

            if(student.cws.size < cw_amount ){
                for(j in 0 until student.cws.size) {
                    stString+=",${student.cws[j]}"
                }
                for(j in student.cws.size until cw_amount) {
                    stString+=","
                }
            }
            else{
                for(j in 0 until test_amount) {
                    stString+=",${student!!.cws[j]}"
                }
            }

            stString+=",${student.pluses},${student.minuses}"
            startString += stString
        }
        return startString
    }

    private fun addTableColumn(label:String, amount:Int, str:String):String{
        var strn = str
        for (i in 1..amount){
            strn += ",$label $i"
        }
        return strn
    }
}