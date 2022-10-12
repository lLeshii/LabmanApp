package roomdb

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.util.*
import java.util.stream.Collectors

class ListConverter {
    @RequiresApi(Build.VERSION_CODES.N)
    @TypeConverter
    fun fromNumbs(numbs: List<String?>): String {
        return numbs.stream().collect(Collectors.joining(","))
    }

    @TypeConverter
    fun toNumbs(data: String): List<String> {
        return data.split(",")
    }
}