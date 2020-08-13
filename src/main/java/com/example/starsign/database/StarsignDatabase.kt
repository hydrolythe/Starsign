package com.example.starsign.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DatabaseCard::class, DatabaseMonster::class, DatabaseMagic::class, DatabaseSource::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class StarsignDatabase : RoomDatabase() {
    abstract val cardDao : CardDao
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