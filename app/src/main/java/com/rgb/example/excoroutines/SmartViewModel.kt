package com.rgb.example.excoroutines

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SmartViewModel : ViewModel() {

    fun doSomeTask(){
        // Using this scope will do the cancellation of jobs in onCleared() automatically.
        // Hence removing the biolerplate code.
        viewModelScope.launch(Dispatchers.IO) {
            delay(4000)
            Log.i("Mytag", "Smart scope done")
        }
    }

}