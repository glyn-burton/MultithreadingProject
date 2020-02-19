package com.example.multithreadingproject

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log

class Looper:Thread() {

    lateinit var  mainHandler : Handler
    lateinit var  myHandler: Handler

    fun handleMessages(passedHandler: Handler){

        mainHandler = passedHandler
        myHandler = MyLooper()


    }

    inner class MyLooper : Handler(Looper.myLooper()!!) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val message = Message()
            message.what = msg.what

            var fiboList = mutableListOf<Int>()
            fiboList.add(0)
            fiboList.add(1)

            for(i in fiboList[1]..19){

                var newNum = 0
                newNum = fiboList[i] + fiboList[i-1]
                fiboList.add(newNum)

            }


            val bundle = Bundle()
            bundle.putString(
                "KEY",
                "Here's the sequence: ${fiboList.toString()}"
            )
            message.data = bundle
            mainHandler.sendMessage(message)

        }




        fun alterString(passedString : String):String = ""

    }

    override fun run() {
        super.run()
        Looper.prepare()
        Looper.loop()
    }
}