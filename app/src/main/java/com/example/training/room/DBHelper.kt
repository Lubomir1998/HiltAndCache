package com.example.training.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Video::class], version = 2)
abstract class DBHelper: RoomDatabase() {

    abstract fun getVideoDao(): VideoDao

}