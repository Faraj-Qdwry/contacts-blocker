package com.example.contactsblocker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object {

        private var appDb: AppDatabase? = null

        @JvmStatic
        fun getAppDb(context: Context): AppDatabase {
            if (appDb == null) {
                appDb = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "contacts_db"
                ).allowMainThreadQueries().build()
            }
            return appDb!!
        }
    }
}