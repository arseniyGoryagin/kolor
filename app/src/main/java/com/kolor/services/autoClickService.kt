package com.kolor.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.kolor.data.DbRepository
import com.kolor.data.KolorApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.processNextEventInCurrentThread

class autoClickService() : Service() {


    private lateinit var repository : DbRepository
    private val scope = CoroutineScope(Dispatchers.Default)



    private fun startAutoClick(){
        scope.launch {
            while(true){
                val autoClickValue = repository.getAutoClickValue()
                var autoClickSpeedValue = repository.getAutoClickSpeedValue()
                autoClickSpeedValue = if(autoClickSpeedValue == 0L){1}else{autoClickSpeedValue}

                delay(1000/autoClickSpeedValue)
                repository.updateCoins(autoClickValue)
            }
        }
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        repository = (application as KolorApplication).container.repository


        startAutoClick()
        return START_STICKY
    }


    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }



}