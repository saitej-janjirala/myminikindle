package com.saitejajanjirala.internshaalaprac2.databases

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface bookdao {
    @Insert
    fun insertbook(bookEntities: BookEntities)

    @Delete
    fun deletebook(bookEntities: BookEntities)

    @Query("select * from books")
    fun getallbooks():List<BookEntities>

    @Query("select * from books where book_id=:bookid")
    fun getbookbyid(bookid:String):BookEntities
}