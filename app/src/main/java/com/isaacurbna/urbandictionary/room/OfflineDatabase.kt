package com.isaacurbna.urbandictionary.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.isaacurbna.urbandictionary.model.data.Term

@Database(
    entities = [Term::class],
    version = 1
)
abstract class OfflineDatabase : RoomDatabase() {

    abstract fun termsDao(): TermsDao

    companion object {
        var INSTANCE: OfflineDatabase? = null

        fun getAppDataBase(context: Context): OfflineDatabase? {
            if (INSTANCE == null) {
                synchronized(OfflineDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        OfflineDatabase::class.java,
                        "OfflineDatabase"
                    )
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }

}