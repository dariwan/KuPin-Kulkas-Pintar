package com.dariwan.kupin.core.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dariwan.kupin.core.data.local.database.kitchencabinet.KitchenCabinet
import com.dariwan.kupin.core.data.local.database.kitchencabinet.KitchenCabinetDao
import com.dariwan.kupin.core.data.local.database.kulkasku.Material
import com.dariwan.kupin.core.data.local.database.kulkasku.MaterialDao


@Database(entities = [Material::class, KitchenCabinet::class], version = 2, exportSchema = false)
abstract class MaterialRoomDatabase: RoomDatabase() {
    abstract fun materialDao(): MaterialDao
    abstract fun kitchenCabinetDao(): KitchenCabinetDao

    companion object{
        @Volatile
        private var INSTANCE: MaterialRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): MaterialRoomDatabase {
            if (INSTANCE == null){
                synchronized(MaterialRoomDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MaterialRoomDatabase::class.java, "material")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as MaterialRoomDatabase
        }
    }
}