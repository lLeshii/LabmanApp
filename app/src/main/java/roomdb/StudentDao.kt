package roomdb
import androidx.lifecycle.LiveData
import androidx.room.*
import roomdb.Student


@Dao
interface StudentDao {
    @Insert
    fun insert(student: Student)

    @Insert
    fun insert(students: List<Student?>?)

    @Update
    fun update(student: Student?)

    @Delete
    fun delete(student: Student?)

    @Query("SELECT * FROM student")
    fun getAll(): LiveData<List<Student?>?>

    @Query("SELECT * FROM student")
    fun getAllNoLive(): List<Student?>?

    @Query("SELECT COUNT (*) FROM student")
    fun getAmount(): Int

    @Query("DELETE from student")
    fun deleteAll()

    @Query("SELECT * FROM student WHERE studentId = :employeeId")
    fun getById(employeeId: Int): Student?

    @Query("SELECT * FROM student WHERE groupName = :grName")
    fun getAllFromOneGroup(grName: String): List<Student?>?

    @Query("DELETE from student WHERE groupName = :grName")
    fun deleteAllFromOneGroup(grName: String)
}