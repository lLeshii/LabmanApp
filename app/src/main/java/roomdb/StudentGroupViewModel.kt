package roomdb

import roomdb.Group
import roomdb.Student
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class StudentGroupViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = StudentGroupRepository(app)
    private val allStudents = repository.getAllStudents()
    private val allGroups = repository.getAllGroups()


    fun insert(student: Student) {
        repository.insert(student)
    }

    fun insert(group: Group) {
        repository.insert(group)
    }

    fun update(student: Student) {
        repository.update(student)
    }

    fun delete(student: Student) {
        repository.delete(student)
    }

    fun update(group: Group) {
        repository.update(group)
    }

    fun delete(group: Group) {
        repository.delete(group)
    }

    fun deleteAllStudents() {
       repository.deleteAllStudents()
    }

    fun amountOfStudents(): Int{
        return repository.amountStud()
    }

    fun amountOfGroups(): Int{
        return repository.amountGroup()
    }

    fun deleteAllStudentsFromOneGroup(grName: String) {
        repository.deleteAllStudentsFromOneGroup(grName)
    }
    fun deleteAllGroups() {
        repository.deleteAllGroups()
    }

    fun deleteGroupByName(grName: String) {
        repository.deleteGroupByName(grName)
    }

    fun getById(id:Int): Student?{
        return repository.getStudById(id)
    }

    fun getAllStudents(): LiveData<List<Student?>?> {
        return allStudents
    }

    fun getAllGroups(): LiveData<List<Group?>?> {
        return allGroups
    }

    fun getAllStudentsNoLive(): List<Student?>? {
        return repository.getAllStudentsNoLive()
    }

    fun getAllGroupsNoLive(): List<Group?>? {
        return repository.getAllGroupsNoLive()
    }

    fun getStudentsFromGroup(name: String): List<Student?>? {
        return repository.getStudentsFromGroup(name)
    }

    fun getGroupByName(name:String): Group?{
        return repository.getGroupByName(name)
    }

    fun getGroupById(name:Int): Group?{
        return repository.getGroupById(name)
    }
}
