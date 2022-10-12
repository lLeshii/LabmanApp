
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import roomdb.StudentGroupViewModel
import javax.inject.Inject

class MyViewModelFactory @Inject constructor(private val repository: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(StudentGroupViewModel::class.java!!)) {
            StudentGroupViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}