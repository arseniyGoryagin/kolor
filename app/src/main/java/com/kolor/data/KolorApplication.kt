package com.kolor.data

import android.app.Application

class KolorApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefAppContainer(this)
    }


}