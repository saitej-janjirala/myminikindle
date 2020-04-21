package com.saitejajanjirala.internshaalaprac2.databases

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BookEntities::class],version = 1)
abstract class BookDatabase: RoomDatabase() {
    abstract fun bookDao():bookdao

}