package com.example.coffeefirst.di

import android.content.Context
import androidx.room.Room
import com.example.coffeefirst.data.db.AppDatabase

object AppDatabaseInstance {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun get(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "coffee_first_db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
