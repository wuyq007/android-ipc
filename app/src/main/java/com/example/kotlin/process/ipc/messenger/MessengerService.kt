package com.example.kotlin.process.ipc.messenger

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import kotlin.concurrent.thread

class MessengerService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return Messenger(ServiceHandler).binder
    }

    private object ServiceHandler : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            //接收到客户端发送来的消息
            val name = msg.data.getString("name")
            val content = msg.data.getString("content")
            val array = arrayOf(1,2,3)

            Log.e("AAA", "Service $name：$content")

            when (msg.what) {
                0 -> {
                    msg.obj = content?.replace("吗", "")?.replace("？", "")
                }
                1 -> {
                    msg.obj = "我没叫啊"
                }
                2 -> {
                    msg.obj = "88"
                }
                else -> {
                    return
                }
            }
            msg.what++
            sendMessageToClient(msg)
        }


        private fun sendMessageToClient(msg: Message) {
            //获取到客户端传递过来的 Messenger
            val service: Messenger = msg.replyTo


            val message: Message = Message.obtain()
            message.what = msg.what

            val bundle = Bundle()
            //传递的参数
            bundle.putInt("id", 1)
            bundle.putString("name", "李四")
            bundle.putString("content", msg.obj.toString())
            message.data = bundle

            service.send(message)
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("AAA", "MessengerService onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("AAA", "MessengerService onDestroy")
        thread {
            Thread.sleep(500)
            //结束当前进程
            Process.killProcess(Process.myPid())
        }
    }

}