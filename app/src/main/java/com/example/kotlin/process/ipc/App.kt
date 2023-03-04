package com.example.kotlin.process.ipc

import android.app.Application
import com.example.kotlin.process.ipc.provider.bean.UserDataManger

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        UserDataManger.getInstance().init(this)
    }

}