package com.example.kotlin.process.ipc.aidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.example.kotlin.process.ipc.BaseActivity
import com.example.kotlin.process.ipc.IpcAidlInterface
import com.example.kotlin.process.ipc.IpcAidlListener
import com.example.kotlin.process.ipc.R
import com.example.kotlin.process.ipc.aidl.bean.AidlMessageBean
import com.example.kotlin.process.ipc.databinding.ActivityAidlBinding
import com.google.gson.GsonBuilder


class AidlActivity : BaseActivity() {

    private lateinit var binding: ActivityAidlBinding

    private lateinit var ipcAidlInterface: IpcAidlInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAidlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle(R.string.aidl)

        binding.btnSend.setOnClickListener {
            val message = AidlMessageBean()
            message.id = 0
            message.name = "张三"
            Log.e("AAA", "AidlClient sendMessage：$" + GsonBuilder().create().toJson(message))
            ipcAidlInterface.sendMessage(message)
        }
        AidlServiceConnection.setContext(this)
        bindService()
    }

    private fun bindService() {
        val intent = Intent()
        //隐式跨端启动，服务端的清单文件中的action
        intent.action = "com.process.ipc.aidl.AidlService"
        intent.setPackage("com.example.kotlin.process.ipc.aidl")
        bindService(
            intent, AidlServiceConnection, Context.BIND_AUTO_CREATE
        )
        Log.e("AAA", "AidlClient bindService")
    }

    private object AidlServiceConnection : ServiceConnection {

        private lateinit var context: AidlActivity
        fun setContext(context: AidlActivity) {
            this.context = context
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            context.ipcAidlInterface = IpcAidlInterface.Stub.asInterface(service)
            Log.e("AAA", "AidlClient onServiceConnected")
            context.ipcAidlInterface.setOnAidlListener(object : IpcAidlListener.Stub() {
                override fun onSendMessageSuccess(messageBean: AidlMessageBean) {
                    Log.e(
                        "AAA",
                        "AidlClient onSendMessageSuccess：" + GsonBuilder().create()
                            .toJson(messageBean)
                    )
                }

                override fun onSendMessageFailed(errorCode: Int) {
                    Log.e("AAA", "AidlClient onServiceDisconnected")
                }
            })
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.e("AAA", "AidlClient onServiceDisconnected")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(AidlServiceConnection)
    }

}