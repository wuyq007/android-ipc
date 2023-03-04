package com.example.kotlin.process.ipc.binder

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.IInterface
import android.os.Process
import android.util.Log
import com.example.kotlin.process.ipc.binder.bean.UserBean
import com.google.gson.Gson
import kotlin.concurrent.thread


class BinderService : Service() {

    companion object {
        fun asInterface(binder: IBinder?): BinderStub? {
            if (binder == null) {
                return null
            }
            val iin: IInterface? = binder.queryLocalInterface(DESCRIPTOR)
            return if (iin != null && iin is BinderStub) {
                //有值：Client 和 Server 在同一进程
                iin
            } else {
                //不同进程需要创建代理对象来实现远程访问
                ProxyBinder(binder)
            }
        }
    }


    override fun onBind(intent: Intent?): IBinder {
        return BinderManager()
    }


    class BinderManager : BinderStub() {

        override fun sendUserInfo(user: UserBean?) {
            Log.e("AAA", "ManagerBinder setUserInfo" + Gson().toJson(user))
        }

        override fun getUserInfo(): UserBean {
            val userBean = UserBean()
            userBean.id = 1
            userBean.name = "李四"
            return userBean
        }
    }


    override fun onCreate() {
        super.onCreate()
        Log.e("AAA", "BinderService onCreate")
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.e("AAA", "BinderService onCreate")
        thread {
            Thread.sleep(500)
            //结束当前进程
            Process.killProcess(Process.myPid())
        }
    }

}