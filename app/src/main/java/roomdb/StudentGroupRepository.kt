package roomdb

import android.app.Application
import androidx.lifecycle.LiveData
import roomdb.utils.subscribeOnBackground

class StudentGroupRepository(application: Application) {

    private var studDao: StudentDao
    private var groupDao: GroupDao
    private var allStudents: LiveData<List<Student?>?>
    private var allGroups: LiveData<List<Group?>?>

    private val database = StudDatabase.getInstance(application)

    init {
        studDao = database.studDao()
        groupDao = database.groupDao()

        allGroups = groupDao.getAll()
        allStudents = studDao.getAll()
    }

    fun insert(student: Student) {
//        Single.just(noteDao.insert(note))
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe()
        subscribeOnBackground {
            studDao.insert(student)
        }
    }

    fun update(student: Student) {
        subscribeOnBackground {
            studDao.update(student)
        }
    }

    fun delete(student: Student) {
        subscribeOnBackground {
            studDao.delete(student)
        }
    }

    fun amountStud(): Int {
        return studDao.getAmount()
    }

    fun amountGroup(): Int {
        return groupDao.getAmount()
    }

    fun deleteAllStudents() {
        subscribeOnBackground {
            studDao.deleteAll()
        }
    }

    fun deleteAllStudentsFromOneGroup(grName: String) {
        subscribeOnBackground {
            studDao.deleteAllFromOneGroup(grName)
        }
    }

    fun getGroup(name:String):List<Student?>?{
        return studDao.getAllFromOneGroup(name)
    }

    fun getGroupByName(name:String):Group?{
        return groupDao.getAllFromOneGroup(name)
    }

    fun getAllStudents(): LiveData<List<Student?>?> {
        return allStudents
    }

    fun insert(group: Group) {
//        Single.just(noteDao.insert(note))
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe()
        subscribeOnBackground {
            groupDao.insert(group)
        }
    }

    fun update(group: Group) {
        subscribeOnBackground {
            groupDao.update(group)
        }
    }

    fun delete(group: Group) {
        subscribeOnBackground {
            groupDao.delete(group)
            studDao.deleteAllFromOneGroup(group.groupName!!)
        }
    }

    fun deleteAllGroups() {
        subscribeOnBackground {
            groupDao.deleteAll()
            studDao.deleteAll()
        }
    }

    fun deleteGroupByName(grName: String) {
        subscribeOnBackground {
            groupDao.deleteOneGroup(grName)
            studDao.deleteAllFromOneGroup(grName)
        }
    }

    fun getAllGroups(): LiveData<List<Group?>?> {
        return allGroups
    }

    fun getAllStudentsNoLive(): List<Student?>? {
        return studDao.getAllNoLive()
    }

    fun getAllGroupsNoLive(): List<Group?>? {
        return groupDao.getAllNoLive()
    }
    fun getStudentsFromGroup(name:String): List<Student?>? {
        return studDao.getAllFromOneGroup(name)
    }

    fun getStudById(id: Int): Student?{
        return studDao.getById(id)
    }

    fun getGroupById(id: Int): Group?{
        return groupDao.getById(id)
    }

}