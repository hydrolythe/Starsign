package com.example.starsign.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class StarsignDatabase : RoomDatabase() {
    abstract val userDao : UserDao
    abstract val cardDao : CardDao
    abstract val userCardDao : UserCardDao
    companion object{
        @Volatile
        private var INSTANCE: StarsignDatabase? = null
        fun getInstance(context: Context): StarsignDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(context.applicationContext, StarsignDatabase::class.java, "Database for deckbuilding").fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}