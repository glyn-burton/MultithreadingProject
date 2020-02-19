package com.example.multithreadingproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.TextView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity(), AsyncTaskerCallback {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister((this))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAsyncTaskEvent(event : AsyncTaskEvent){
        tvPrimeStringOutput.text = event.resultmessage

    }

    fun runReverseStringThread(){
        val thread = Thread(Runnable {

            val stringInput = etReverseStringInput.text.toString()

            var reverseOutput: String

            reverseOutput = stringInput.reversed()

            tvReverseStringOutput.text = reverseOutput

        })
        thread.start()

    }

    fun onClick(view: View) {

        when(view.id){

            R.id.btnReverseString -> runReverseStringThread()

            R.id.btnCalculatePrimes -> {

                var async = AsyncTasker()
                val num1 = etFirstNumberInput.text.toString().toInt()
                val num2 = etSecondNumberInput.text.toString().toInt()
                val params = ListParams(num1, num2)
                async.execute(params)
            }
            R.id.btnFibonacci -> {
                onLooperExecuted("")

            }


        }


    }

    override fun onResult(result: String?) {

        tvPrimeStringOutput.text = result?:"No number input"
    }

    fun onLooperExecuted(valuePassed:String){

        val looperDemo = Looper()
        looperDemo.handleMessages(Handler(Looper.getMainLooper()){
                message ->
            tvFibonacci.text = message.data.getString("KEY")
            true

        })

        looperDemo.start()
        val message = Message()
        message.data.putString("KEY_MAIN", valuePassed)
        looperDemo.myHandler.sendMessage(message)
    }
}
