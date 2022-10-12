package roomdb
import roomdb.Student
import roomdb.Group
import roomdb.GroupDao
import roomdb.StudentDao
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Group::class, Student::class], version = 3)
@TypeConverters(ListConverter::class)
abstract class StudDatabase: RoomDatabase() {
    abstract fun studDao(): StudentDao
    abstract fun groupDao(): GroupDao

    companion object {
        private var instance: StudDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): StudDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, StudDatabase::class.java,
                    "stud_database"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            return instance!!
        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase(instance!!)
            }
        }

        private fun populateDatabase(db: StudDatabase) {
            val studDao = db.studDao()
            val groupDao = db.groupDao()
        }
    }
}