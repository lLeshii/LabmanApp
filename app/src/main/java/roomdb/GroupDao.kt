package roomdb
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface GroupDao {
    @Insert
    fun insert(groups: List<Group?>?)
    @Insert
    fun insert(Group: Group)

    @Update
    fun update(group: Group?)

    @Delete
    fun delete(group: Group?)

    @Query("SELECT * FROM `group`")
    fun getAll(): LiveData<List<Group?>?>

    @Query("SELECT * FROM `group`")
    fun getAllNoLive(): List<Group?>?

    @Query("SELECT COUNT (*) FROM `group`")
    fun getAmount(): Int

    @Query("SELECT * FROM `group` WHERE GroupId = :employeeId")
    fun getById(employeeId: Int): Group?

    @Query("SELECT * FROM `group` WHERE groupName = :grName")
    fun getAllFromOneGroup(grName: String): Group?

    @Query("DELETE from `group` WHERE groupName = :grName")
    fun deleteOneGroup(grName: String)

    @Query("DELETE from `group`")
    fun deleteAll()
}