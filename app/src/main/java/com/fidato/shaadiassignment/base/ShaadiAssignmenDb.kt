package com.fidato.shaadiassignment.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fidato.shaadiassignment.main.data.dao.MatchesDao
import com.fidato.shaadiassignment.model.MatchesModel

@Database(entities = [MatchesModel::class], version = 1, exportSchema = false)
abstract class ShaadiAssignmenDb : RoomDatabase() {

    abstract fun matchesDao(): MatchesDao

    companion object {

        @Volatile
        private var INSTANCE: ShaadiAssignmenDb? = null

        fun getDatabase(context: Context): ShaadiAssignmenDb? {
            if (INSTANCE == null) {
                synchronized(ShaadiAssignmenDb::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            ShaadiAssignmenDb::class.java, "shaadi_assignmen_db"
                        ).build()
                    }
                }
            }
            return INSTANCE
        }
    }

}