package com.example.kotlin.process.ipc.provider

import android.app.Activity
import android.content.ContentProvider
import android.content.ContentResolver
import android.content.ContentValues
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

/**
 *
 */
class IPCContentProvider : ContentProvider() {

    companion object {
        private const val TAG = "ContentProvider"

        //注意 Manifest 文件里的 authorities 属性要跟这里保持一致。
        private const val AUTHORITY = "com.ipc.process.contentProvider"
        private val uri: Uri = Uri.parse("content://$AUTHORITY")

        val uriAdd: Uri = Uri.parse("content://$AUTHORITY?type=0")
        val uriUpdate: Uri = Uri.parse("content://$AUTHORITY?type=1")
        val uriQuery: Uri = Uri.parse("content://$AUTHORITY?type=2")
        val uriDelete: Uri = Uri.parse("content://$AUTHORITY?type=3")

        /**
         * 在 A进程中注册 ContentObserver
         * 注册内容观察者
         */
        fun register(activity: Activity, contentObserver: ContentObserver) {
            activity.contentResolver.registerContentObserver(
                uri, true, contentObserver
            )
        }

        /**
         * 在 A进程中反注册 ContentObserver
         * 解除观察者
         */
        fun unRegister(activity: Activity, contentObserver: ContentObserver) {
            activity.contentResolver.unregisterContentObserver(contentObserver)
        }

        /**
         * 在 B进程中通知 ContentObserver 刷新
         *
         * 通知 ContentProvider 刷新了
         */
        fun notifyUriChange(activity: Activity, uri: Uri) {
            activity.contentResolver?.notifyChange(uri, null)
        }
    }

    override fun onCreate(): Boolean {
        return false
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        Log.e(TAG, "IPCContentProvider query")
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.e(TAG, "IPCContentProvider insert")
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        Log.e(TAG, "IPCContentProvider delete")
        return 0
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?
    ): Int {
        Log.e(TAG, "IPCContentProvider update")
        return 0
    }

}