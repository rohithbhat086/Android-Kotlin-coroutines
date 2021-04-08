package com.rgb.example.excoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    var count : Int = 0
    private lateinit var vModel : SimpleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vModel = ViewModelProvider(this).get(SimpleViewModel::class.java)
        vModel.doSomeTask()

        CoroutineScope(Dispatchers.Main).launch {
            var finalContent = getContent(20)

            Toast.makeText(applicationContext, "Result: $finalContent", Toast.LENGTH_SHORT).show()
        }

        val tv = findViewById<TextView>(R.id.textView)

        findViewById<Button>(R.id.button_sample).setOnClickListener {
            tv.text = (++count).toString()
        }
    }

    private suspend fun getContent(initial: Int): Int {
        var subResult = 0; var subResult2 = 0 ; var total = 0; var subResult3 = 0; var subResult4 = 0;
        coroutineScope {                                                //suspending function. Returns after all child coroutines are done
            launch(Dispatchers.IO) {                                    // Parallel execution
                Log.i("MYTAG", "1 ---------------")
                delay(5000)
                Log.i("MYTAG", "4 ---------------")
                subResult = 30
            }

            subResult2 = async(Dispatchers.IO) {                        // Parallel execution
                Log.i("MYTAG", "2 ---------------")
                delay(3000)
                Log.i("MYTAG", "3 ---------------")
                return@async 50
            }.await()
        }

        Log.i("MYTAG", "5 ---------------")
        total = initial + subResult + subResult2

        coroutineScope {
            withContext(Dispatchers.IO){                                // synchronous execution.
                launch {
                    Log.i("MYTAG", "6 ---------------")
                    delay(5000)
                    Log.i("MYTAG", "7 ---------------")
                    total += 30
                }
            }
            Log.i("MYTAG", "8 ---------------")
            launch(Dispatchers.IO) {
                Log.i("MYTAG", "9 ---------------")
                delay(5000)
                Log.i("MYTAG", "12 ---------------")
                subResult3 = 30
            }

            subResult4 = async(Dispatchers.IO) {
                Log.i("MYTAG", "10 ---------------")
                delay(3000)
                Log.i("MYTAG", "11 ---------------")
                return@async 50
            }.await()
        }

        Log.i("MYTAG", "13 ---------------")
        total += subResult3 + subResult4

        return total
    }
}