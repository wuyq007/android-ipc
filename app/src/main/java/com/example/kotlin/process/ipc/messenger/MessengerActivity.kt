package com.example.kotlin.process.ipc.messenger

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import com.example.kotlin.process.ipc.BaseActivity
import com.example.kotlin.process.ipc.R
import com.example.kotlin.process.ipc.databinding.ActivityMessengerBinding

class MessengerActivity : BaseActivity() {

    private lateinit var binding: ActivityMessengerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessengerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle(R.string.messenger)

        binding.btnSend.setOnClickListener {
            ClientHandler.handleMessage(Message.obtain(null, 0))
        }
        bindService()
    }

    private fun bindService() {
        val intent = Intent(this, MessengerService::class.java)
        bindService(intent, MessengerServiceConnection, Context.BIND_AUTO_CREATE)
        Log.e("AAA", "MessengerActivity bindService")
    }

    private object MessengerServiceConnection : ServiceConnection {

        private var client: Messenger? = null

        //为了接收服务端的回复，客户端也需要准备一个接收消息的Messenger 和Handler
        private val clientMessenger: Messenger = Messenger(ClientHandler)

        fun sendMessageToService(what: Int, content: String) {
            val message: Message = Message.obtain()
            message.what = what

            //Client 发信时指定希望回信人，把客户端进程的Messenger对象设置到Message中
            message.replyTo = clientMessenger

            // 跨进程不能传递 message.obj，只能使用 bundle
            val bundle = Bundle()
            //传递的参数
            bundle.putInt("id", 0)
            bundle.putString("name", "张三")
            bundle.putString("content", content)
            message.data = bundle

            //跨进程传递
            client?.send(message)
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.e("AAA", "MessengerActivity onServiceConnected")
            //把返回的IBinder对象初始化Messenger
            client = Messenger(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.e("AAA", "MessengerActivity onServiceDisconnected")
        }
    }

    private object ClientHandler : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            //接收到服务端发送来的消息
            val name = msg.data.getString("name")
            val content = msg.data.getString("content")

            name?.let {
                Log.e("AAA", " Client $name：$content")
            }
            when (msg.what) {
                0 -> {
                    MessengerServiceConnection.sendMessageToService(0, "您好吗？")
                }
                1 -> {
                    MessengerServiceConnection.sendMessageToService(msg.what, "你叫什么？")
                }
                2 -> {
                    MessengerServiceConnection.sendMessageToService(msg.what, "拜拜！")
                }
                else -> {
                    return
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(MessengerServiceConnection)
    }

}