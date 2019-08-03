package com.simplu.pingpongscore.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Match::class], version = 1, exportSchema = false)
abstract class MatchDatabase : RoomDatabase() {

    abstract val matchDao: MatchDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: MatchDatabase? = null

        fun getDatabase(context: Context): MatchDatabase {

            synchronized(this) {

                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, MatchDatabase::class.java, "match_table").
                            fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance

            }

        }

    }

}