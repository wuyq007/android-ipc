package com.example.kotlin.process.ipc.provider

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.kotlin.process.ipc.BaseActivity
import com.example.kotlin.process.ipc.R
import com.example.kotlin.process.ipc.databinding.ActivityContentProviderBinding
import com.example.kotlin.process.ipc.provider.bean.UserBean
import com.example.kotlin.process.ipc.provider.bean.UserDataManger
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import java.util.*

class ContentProviderActivity : BaseActivity() {

    private lateinit var contentObserver: IPCContentObserver

    private lateinit var binding: ActivityContentProviderBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentProviderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle(R.string.content_provider)

        binding.btnSend.setOnClickListener {
            //启动一个新进程
            startActivity(Intent(this, ContentProviderNewProcessActivity::class.java))
        }

        //注册 ContentObserver 监听
        contentObserver = IPCContentObserver()
        IPCContentProvider.register(this, contentObserver)

        launch {
            val count = UserDataManger.getDao().getCount()
            if (count == 0) {
                UserDataManger.getDao().insert(UserBean("张三", 20, "男", 80))
                UserDataManger.getDao().insert(UserBean("李四", 25, "男", 70))
                UserDataManger.getDao().insert(UserBean("王五", 30, "男", 60))
                UserDataManger.getDao().insert(UserBean("老六", 35, "男", 90))
            }
            val userArray = UserDataManger.getDao().getAll()
            userArray.forEach {
                Log.e("ContentProvider", GsonBuilder().create().toJson(it))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //解绑 ContentObserver
        IPCContentProvider.unRegister(this, contentObserver)
    }

}