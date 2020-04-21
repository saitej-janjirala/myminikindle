package com.saitejajanjirala.internshaalaprac2.databases

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntities(
    @PrimaryKey val book_id:Int,
    @ColumnInfo(name="book_name")val bookname:String,
    @ColumnInfo(name="author_name") val authorname:String,
    @ColumnInfo(name="book_price")val bookprice:String,
    @ColumnInfo(name="book_rating")val bookrating:String,
    @ColumnInfo(name="book_image")val bookImage:String,
    @ColumnInfo(name="book_desc")val bookdesc:String
)
