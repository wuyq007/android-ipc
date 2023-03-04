package com.example.kotlin.process.ipc.provider.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = UserBean.TABLE_NAME)
class UserBean {

    companion object {
        const val TABLE_NAME = "Table_User"
    }

    /**
     * Room 只能有一个构造函数
     */
    constructor(name: String, age: Int, gender: String, grade: Int) {
        this.name = name
        this.age = age
        this.gender = gender
        this.grade = grade
    }

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var name: String? = null

    var age: Int? = null

    var gender: String? = null

    /**
     * version = 2
     */
    var grade: Int? = null
}