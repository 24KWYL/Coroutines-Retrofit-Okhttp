import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {
    companion object {
        const val TAG = "MainViewModel"
    }

    private val mainScope = MainScope()

    private val repertory: MainRepository by lazy { MainRepository() }
    var data: MutableLiveData<JsonBean> = MutableLiveData()

    fun getDataFromServer() = mainScope.launch {
        val jsonBeanList = withContext(Dispatchers.IO) {
            Log.d(TAG, "${Thread.currentThread()}")
            repertory.getDataFromServer()
        }
        data.postValue(jsonBeanList)
    }

    override fun onCleared() {
        super.onCleared()
        mainScope.cancel()
    }

}