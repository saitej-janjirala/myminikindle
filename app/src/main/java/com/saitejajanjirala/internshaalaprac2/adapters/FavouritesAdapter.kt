package com.saitejajanjirala.internshaalaprac2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.saitejajanjirala.internshaalaprac2.R
import com.saitejajanjirala.internshaalaprac2.databases.BookEntities
import com.squareup.picasso.Picasso

class FavouritesAdapter(val context: Context,val booklist: List<BookEntities>): RecyclerView.Adapter<FavouritesAdapter.FavouritesviewHolder>() {

    class FavouritesviewHolder(view:View):RecyclerView.ViewHolder(view){
        val txtBookname: TextView=view.findViewById(R.id.txtFavBookTitle)
        val txtBookAuthor:TextView=view.findViewById(R.id.txtFavBookAuthor)
        val txtBookprice:TextView=view.findViewById(R.id.txtFavBookPrice)
        val txtBookrating:TextView=view.findViewById(R.id.txtFavBookRating)
        val imagecontent:ImageView=view.findViewById(R.id.imgFavBookImage)
        val llcontent:LinearLayout=view.findViewById(R.id.llFavContent)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesviewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.recycler_favourite_single_row,parent,false)
        return FavouritesviewHolder(view)
        //To change body of created functions use File | Settings | File Templates.

    }

    override fun getItemCount(): Int {
        return booklist.size
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: FavouritesviewHolder, position: Int) {
        val book=booklist[position]
        holder.txtBookname.text=book.bookname
        holder.txtBookAuthor.text=book.authorname
        holder.txtBookprice.text=book.bookprice
        holder.txtBookrating.text=book.bookrating
        Picasso.get().load(book.bookImage).error(R.drawable.defaultbookcover).into(holder.imagecontent)

         //To change body of created functions use File | Settings | File Templates.
    }
}