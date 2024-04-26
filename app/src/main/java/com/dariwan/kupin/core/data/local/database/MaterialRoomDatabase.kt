package com.dariwan.kupin.core.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Material::class], version = 1)
abstract class MaterialRoomDatabase: RoomDatabase() {
    abstract fun materialDao(): MaterialDao

    companion object{
        @Volatile
        private var INSTANCE: MaterialRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): MaterialRoomDatabase {
            if (INSTANCE == null){
                synchronized(MaterialRoomDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MaterialRoomDatabase::class.java, "material")
                        .build()
                }
            }
            return INSTANCE as MaterialRoomDatabase
        }
    }
}