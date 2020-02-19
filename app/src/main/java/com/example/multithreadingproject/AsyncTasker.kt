package com.example.multithreadingproject

import android.os.AsyncTask
import android.util.Log
import android.widget.TextView
import org.greenrobot.eventbus.EventBus
import java.lang.ref.WeakReference

class ListParams internal constructor(
    internal var num1: Int,
    internal var num2: Int
)

class AsyncTasker:AsyncTask<ListParams,String,String>() {


    override fun onPreExecute(){
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: ListParams): String {

        var num1 = params[0].num1
        var num2 = params[0].num2
        var primeList = mutableListOf<String>()

        while (num1 < num2) {

            if (primeNumberChecker(num1)) {

                primeList.add(" "+ num1.toString())

                num1++

            }
            else{

                num1++

            }


        }
        Log.d("NUM", primeList.toString())
        return primeList.toString()
    }

    fun primeNumberChecker(num: Int):Boolean{

        var flag = true
        for (i in 2..num / 2) {
            if (num % i == 0) {
                flag = false
                break
            }
        }
        return flag

    }


    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        Log.d("NUM", result)
        EventBus.getDefault().post(AsyncTaskEvent(result?:"No Result"))


    }

}

interface AsyncTaskerCallback {


    fun onResult(result: String?){


    }

}