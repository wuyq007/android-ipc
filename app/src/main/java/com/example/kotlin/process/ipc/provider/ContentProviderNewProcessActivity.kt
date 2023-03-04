package com.example.kotlin.process.ipc.provider

import android.os.Bundle
import android.os.Process
import com.example.kotlin.process.ipc.BaseActivity
import com.example.kotlin.process.ipc.R
import com.example.kotlin.process.ipc.databinding.ActivityContentProviderNewProcessBinding
import com.example.kotlin.process.ipc.provider.bean.UserBean
import com.example.kotlin.process.ipc.provider.bean.UserDataManger
import kotlinx.coroutines.runBlocking
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class ContentProviderNewProcessActivity : BaseActivity() {

    companion object {
        private const val TAG = "ContentProvider"
    }

    private lateinit var binding: ActivityContentProviderNewProcessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentProviderNewProcessBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setTitle(R.string.new_process)

        binding.btnAdd.setOnClickListener {
            runBlocking {
                UserDataManger.getDao().insert(UserBean("老八", 40, "男", 80))
            }
//            contentResolver.insert(IPCContentProvider.uriAdd, ContentValues())
            //通知 ContentProvider 刷新
            IPCContentProvider.notifyUriChange(this, IPCContentProvider.uriAdd)
        }

        binding.btnUpdate.setOnClickListener {
            runBlocking {
                val userBean = UserDataManger.getDao().getUser("老八")
                userBean?.age = 33
                userBean?.gender = "女"
                userBean?.grade = 66
                userBean?.let {
                    UserDataManger.getDao().update(it)
                }
            }

//            contentResolver.update(IPCContentProvider.uriUpdate, ContentValues(), null, null)

            //通知 ContentProvider 刷新
            IPCContentProvider.notifyUriChange(this, IPCContentProvider.uriUpdate)
        }

        binding.btnQuery.setOnClickListener {
            runBlocking {
                UserDataManger.getDao().getUser("老八")
            }

//            contentResolver.query(IPCContentProvider.uriQuery, null, null, null, null)

            //通知 ContentProvider 刷新
            IPCContentProvider.notifyUriChange(this, IPCContentProvider.uriQuery)
        }

        binding.btnDelete.setOnClickListener {
            runBlocking {
                UserDataManger.getDao().deleteByName("老八")
            }
//            contentResolver.delete(IPCContentProvider.uriDelete, null, null)
            //通知 ContentProvider 刷新
            IPCContentProvider.notifyUriChange(this, IPCContentProvider.uriDelete)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        thread {
            sleep(500)
            //结束当前进程
            Process.killProcess(Process.myPid())
        }
    }

}