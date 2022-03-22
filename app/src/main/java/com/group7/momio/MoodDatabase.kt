package com.group7.momio

import android.content.Context
import androidx.room.*

@Database(version = 1, entities = [MoodMonth::class])
@TypeConverters(Converters::class)
abstract class MoodDatabase : RoomDatabase() {

    abstract fun getDao(): DataAccess

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MoodDatabase? = null

        fun getDatabase(context: Context): MoodDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoodDatabase::class.java,
                    "Efe"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}