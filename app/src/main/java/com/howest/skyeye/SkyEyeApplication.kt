package com.howest.skyeye

import android.app.Application
import com.howest.skyeye.data.AppContainer
import com.howest.skyeye.data.AppDataContainer

class SkyEyeApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}