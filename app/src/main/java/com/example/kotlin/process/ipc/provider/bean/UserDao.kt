package com.example.kotlin.process.ipc.provider.bean

import androidx.room.*

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: UserBean)

    @Update
    suspend fun update(user: UserBean)

    @Query("select * from Table_User")
    suspend fun getAll(): MutableList<UserBean>

    @Query("select count(id) from Table_User")
    suspend fun getCount(): Int

    @Query("select count(id) from Table_User")
    suspend fun count(): Int

    @Query("select * from Table_User where name = :name ")
    suspend fun getUser(name: String): UserBean?

    @Query("delete from Table_User where name = :name ")
    suspend fun deleteByName(name: String)

    @Delete
    suspend fun delete(user: UserBean)

    @Query("delete from Table_User")
    suspend fun deleteAll()

}