package com.rgb.example.excoroutines

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class SimpleViewModel : ViewModel() {

    private val myJob : Job by lazy {
        Job()
    }

    private val myScope : CoroutineScope by lazy {
        CoroutineScope(Dispatchers.IO + myJob)
    }

    fun doSomeTask(){
        myScope.launch {
            delay(5000)
            Log.i("Mytag", "Some task done")
        }
    }

    override fun onCleared() {
        super.onCleared()
        myJob.cancel()
    }
}