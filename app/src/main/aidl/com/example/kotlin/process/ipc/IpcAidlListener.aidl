// IpcAidlListener.aidl
package com.example.kotlin.process.ipc;

/**
 * 引用了实体类 AidlMessageBean
 */
import com.example.kotlin.process.ipc.aidl.bean.AidlMessageBean;

interface IpcAidlListener {
    void onSendMessageSuccess(in AidlMessageBean message);
    void onSendMessageFailed(int errorCode);
}