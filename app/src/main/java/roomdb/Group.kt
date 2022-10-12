package roomdb
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import com.google.gson.annotations.Expose

@Entity
public data class Group(
   @Expose @PrimaryKey(autoGenerate = true) val groupId: Int,
    val groupName: String?,
    val labAmount: Int?,
    val testAmount: Int?,
    val cwAmount: Int?
)