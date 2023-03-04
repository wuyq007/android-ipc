package com.example.kotlin.process.ipc.aidl.bean

import android.os.Parcel
import android.os.Parcelable

class AidlMessageBean() : Parcelable {

    var id: Long = 0
    var name: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        name = parcel.readString()
    }

    /**
     * 手动添加 readFromParcel
     */
    fun readFromParcel(parcel: Parcel) {
        this.id = parcel.readLong()
        this.name = parcel.readString()
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
    }


    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AidlMessageBean> {
        override fun createFromParcel(parcel: Parcel): AidlMessageBean {
            return AidlMessageBean(parcel)
        }

        override fun newArray(size: Int): Array<AidlMessageBean?> {
            return arrayOfNulls(size)
        }
    }

}