package com.example.kotlin.process.ipc.binder

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.example.kotlin.process.ipc.BaseActivity
import com.example.kotlin.process.ipc.R
import com.example.kotlin.process.ipc.binder.bean.UserBean
import com.example.kotlin.process.ipc.databinding.ActivityBinderBinding
import com.google.gson.Gson


class BinderActivity : BaseActivity() {

    private lateinit var binding: ActivityBinderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBinderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle(R.string.binder)

        binding.btnGet.setOnClickListener {
            BinderServiceConnection.getUserInfo()
        }
        binding.btnSend.setOnClickListener {
            BinderServiceConnection.setUserInfo()
        }
        bindService()
    }

    private fun bindService() {
        val intent = Intent(this, BinderService::class.java)
        bindService(intent, BinderServiceConnection, Context.BIND_AUTO_CREATE)
        Log.e("AAA", "BinderActivity bindService")
    }


    private object BinderServiceConnection : ServiceConnection {
        private var binderSub: BinderStub? = null

        fun getUserInfo() {
            val userBean = binderSub?.getUserInfo()
            Log.e("AAA", "BinderActivity getUserInfo:" + Gson().toJson(userBean))
        }

        fun setUserInfo() {
            val userBean = UserBean()
            userBean.id = 0
            userBean.name = "张三"
            binderSub?.sendUserInfo(userBean)
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.e("AAA", "BinderActivity onServiceConnected")
            binderSub = BinderService.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.e("AAA", "BinderActivity onServiceDisconnected")
        }
    }

}