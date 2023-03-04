package com.example.kotlin.process.ipc.provider.bean

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class UserDataManger {

    private object UserDataMangerHolder {
        val INSTANCE = UserDataManger()
    }

    companion object {
        private const val DB_NAME = "UserData.db"
        private val MIGRATIONS = arrayOf(Migration_1_2)

        fun getInstance() = UserDataMangerHolder.INSTANCE

        fun getDao() = getInstance().getUserDao
    }

    private lateinit var application: Application

    fun init(application: Application) {
        this.application = application
    }

    private val getUserDao: UserDao by lazy {
        val db = Room.databaseBuilder(
            application.applicationContext, UserDatabase::class.java, DB_NAME
        ).addCallback(CreatedCallBack).addMigrations(*MIGRATIONS).build()
        db.userDao
    }

    private object CreatedCallBack : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            //在新装app时会调用，调用时机为数据库build()之后，数据库升级时不调用此函数
            MIGRATIONS.map {
                it.migrate(db)
            }
        }
    }

    /**
     * 版本 1 升级到 2
     */
    private object Migration_1_2 : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            //新增 grade 字段，设置默认值为 50
            database.execSQL("ALTER TABLE " + UserBean.TABLE_NAME + " ADD COLUMN `grade` INTEGER DEFAULT 50")
        }
    }

}