// IpcAidlInterface.aidl
package com.example.kotlin.process.ipc;

import com.example.kotlin.process.ipc.aidl.bean.AidlMessageBean;
import com.example.kotlin.process.ipc.IpcAidlListener;

interface IpcAidlInterface {

    /**
     * in 输入 从客户端 -> 服务端
     * out 输出 从服务器 -> 客户端
     * inout 输入输出 服务器 <=> 客户端
     */
     void sendMessage(in AidlMessageBean message);


    /**
     * 服务端的监听，实现两边通讯
     */
     void setOnAidlListener(in IpcAidlListener listener);

}