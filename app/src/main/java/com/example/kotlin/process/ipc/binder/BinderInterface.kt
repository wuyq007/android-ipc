package com.example.kotlin.process.ipc.binder

import android.os.Binder
import android.os.IBinder
import android.os.IInterface
import android.os.Parcel
import com.example.kotlin.process.ipc.binder.bean.UserBean


const val DESCRIPTOR = "com.example.kotlin.process.ipc.binder.BinderService"
const val TRANSACTION_GET = IBinder.FIRST_CALL_TRANSACTION
const val TRANSACTION_SET = IBinder.FIRST_CALL_TRANSACTION + 1

interface BinderInterface : IInterface {

    fun sendUserInfo(user: UserBean?)

    fun getUserInfo(): UserBean?

}


abstract class BinderStub : Binder(), BinderInterface {

    init {
        this.attachInterface(this, DESCRIPTOR)
    }

    override fun asBinder(): IBinder {
        return this
    }

    override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
        when (code) {
            INTERFACE_TRANSACTION -> {
                reply?.writeString(DESCRIPTOR)
                return true
            }
            TRANSACTION_GET -> {
                val result = this.getUserInfo()
                reply?.writeNoException()
                reply?.writeTypedList(arrayListOf(result))
                return true
            }
            TRANSACTION_SET -> {
                data.enforceInterface(DESCRIPTOR)
                var user: UserBean? = null
                if (data.readInt() != 0) {
                    user = UserBean.createFromParcel(data)
                }
                this.sendUserInfo(user)
                reply?.writeNoException()
                return true
            }
            else -> {
                return super.onTransact(code, data, reply, flags)
            }
        }
    }
}


class ProxyBinder(private val binder: IBinder) : BinderStub() {

    init {
        this.attachInterface(this, DESCRIPTOR)
    }

    override fun getUserInfo(): UserBean? {
        val data = Parcel.obtain()
        val replay = Parcel.obtain()
        val result: ArrayList<UserBean?>?
        try {
            data.writeInterfaceToken(DESCRIPTOR)
            binder.transact(TRANSACTION_GET, data, replay, 0)
            replay.readException()
            result = replay.createTypedArrayList(UserBean.CREATOR)
            return result?.get(0)
        } catch (_: Exception) {
        } finally {
            replay.recycle()
            data.recycle()
        }
        return null
    }

    override fun sendUserInfo(user: UserBean?) {
        val data = Parcel.obtain()
        val replay = Parcel.obtain()
        try {
            data.writeInterfaceToken(DESCRIPTOR)
            if (user != null) {
                data.writeInt(1)
                user.writeToParcel(data, 0)
            } else {
                data.writeInt(0)
            }
            binder.transact(TRANSACTION_SET, data, replay, 0)
            replay.readException()
        } catch (_: Exception) {
        } finally {
            replay.recycle()
            data.recycle()
        }
    }

    override fun asBinder(): IBinder {
        return binder
    }
}
