package com.example.kotlin.process.ipc.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.example.kotlin.process.ipc.IpcAidlInterface
import com.example.kotlin.process.ipc.IpcAidlListener
import com.example.kotlin.process.ipc.aidl.bean.AidlMessageBean
import com.google.gson.GsonBuilder

class AidlService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.e("AAA", "AidlService onCreate")
    }

    override fun onBind(intent: Intent?): IBinder? {
          return AIDLBinder
    }

    private object AIDLBinder : IpcAidlInterface.Stub() {

        private lateinit var aidlListener: IpcAidlListener

        @Throws(RemoteException::class)
        override fun sendMessage(message: AidlMessageBean) {
            Log.e("AAA", "AidlService getMessage：" + GsonBuilder().create().toJson(message))
            message.name = "服务端的数据"
            aidlListener.onSendMessageSuccess(message)
        }

        @Throws(RemoteException::class)
        override fun setOnAidlListener(listener: IpcAidlListener) {
            aidlListener = listener
        }

    }

}