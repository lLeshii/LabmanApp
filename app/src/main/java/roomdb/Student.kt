package roomdb
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import roomdb.ListConverter

@Entity
public data class Student(
    @PrimaryKey(autoGenerate = true) val studentId: Int,
    val studentName: String?,
    val groupName: String?,
    @TypeConverters(ListConverter::class)
    var labs: List<String>,
    @TypeConverters(ListConverter::class)
    var tests: List<String>,
    @TypeConverters(ListConverter::class)
    var cws: List<String>,
    var pluses: Int?,
    var minuses: Int?
)