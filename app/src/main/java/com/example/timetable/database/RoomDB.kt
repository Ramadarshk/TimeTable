package com.example.timetable.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataOfSlot::class], version = 1)
abstract class RoomDB: RoomDatabase() {
    abstract fun slotDao(): SlotDao
    companion object{
        private var INSTANCE:RoomDB?=null
        fun getDataBase(context: Context/* , applicationScope: CoroutineScope*/):RoomDB{
            return INSTANCE?: synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    "slot_database"
                )/*.addCallback(databaseCallback(applicationScope))*/
                    .build()
                INSTANCE=instance
                instance
            }
        }

/*        private class databaseCallback(private val applicationScope1: CoroutineScope): RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                INSTANCE?.let {it1->
                    applicationScope1.launch {
                        populateDatabase(it1.slotDao())
                    }
                }
            }

        }

        private suspend fun populateDatabase(slotDao: SlotDao) {
            // Delete all content here.
            slotDao.deleteAll()
        }*/
    }
}