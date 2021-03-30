package com.mx.mundet.eats.bd

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mx.mundet.eats.BuildConfig
import com.mx.mundet.eats.bd.Dao.PersonasDao
import com.mx.mundet.eats.bd.Entity.PersonasEntity

/**
 * Created by Alexander Juárez with Date 18/03/2021
 * @author Alexander Juárez
 */

@Database(
    entities = [PersonasEntity::class],
    version = BuildConfig.DATABASE_VERSION,
    exportSchema = false
)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun personasDao(): PersonasDao

    companion object {

        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getInstance(application: Application): AppRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    application,
                    AppRoomDatabase::class.java,
                    BuildConfig.DATABASE_NAME,
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }

        }
    }

}