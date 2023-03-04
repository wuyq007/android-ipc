package com.example.kotlin.process.ipc.provider.bean

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(
    version = 2, exportSchema = false, entities = [UserBean::class],
//    autoMigrations = [AutoMigration(from = 1, to = 2)]
)
abstract class UserDatabase : RoomDatabase() {

    val userDao: UserDao by lazy { createUserDao() }

    abstract fun createUserDao(): UserDao

}