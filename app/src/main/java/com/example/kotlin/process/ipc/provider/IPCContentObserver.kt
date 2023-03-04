package com.example.kotlin.process.ipc.provider

import android.database.ContentObserver
import android.net.Uri
import android.util.Log
import com.example.kotlin.process.ipc.provider.bean.UserDataManger
import com.google.gson.GsonBuilder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * 当 ContentProvider 中的数据发生变化（增、删、改）时,会触发 onChange
 */
class IPCContentObserver : ContentObserver(null) {

    companion object {
        private const val TAG = "ContentProvider"
    }

    override fun onChange(selfChange: Boolean, uri: Uri?) {
        super.onChange(selfChange, uri)

        uri?.let {
            val type = it.getQueryParameter("type")
            Log.e(TAG, "A 进程收到刷新数据更新 uri type:$type")
        }

        GlobalScope.launch {
            val userArray = UserDataManger.getDao().getAll()
            userArray.forEach {
                Log.e("ContentProvider", GsonBuilder().create().toJson(it))
            }
        }

    }

}