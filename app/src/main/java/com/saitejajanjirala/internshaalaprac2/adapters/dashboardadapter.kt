package com.saitejajanjirala.internshaalaprac2.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.saitejajanjirala.internshaalaprac2.R
import com.saitejajanjirala.internshaalaprac2.activity.DescriptionActivity
import com.saitejajanjirala.internshaalaprac2.model.Book
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.exampleitemfordashboard.view.*

 class dashboardadapter(val context: Context,val marrayList: ArrayList<Book>):RecyclerView.Adapter<dashboardadapter.dashboardviewholder>() {
     //lateinit var mbookarraylist:ArrayList<Book>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): dashboardviewholder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.exampleitemfordashboard,parent,false)
        return dashboardviewholder(view)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

     override fun getItemCount(): Int {
         return marrayList.size
         TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
     }

     override fun onBindViewHolder(holder: dashboardviewholder, position: Int) {
         holder.bookname.setText(marrayList.get(position).bookName)
         holder.bookauthor.setText(marrayList.get(position).bookAuthor)
         holder.bookrating.setText(marrayList.get(position).bookRating)
         holder.bookprice.setText(marrayList.get(position).bookPrice)
         Picasso.get().load(marrayList.get(position).bookImage).error(R.drawable.defaultbookcover).into(holder.bookimage)
         holder.mcardview.setOnClickListener {
             val intent=Intent(context,DescriptionActivity::class.java)
             intent.putExtra("bookid",marrayList.get(position).bookId)
             context.startActivity(intent)

         }
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
     }
     class dashboardviewholder(view: View):RecyclerView.ViewHolder(view){
         val bookname:TextView=view.findViewById(R.id.bookname)
         val bookauthor:TextView=view.findViewById(R.id.bookauthorname)
         val bookimage:ImageView=view.findViewById(R.id.bookimage)
         val bookrating:TextView=view.findViewById(R.id.bookrating)
         val bookprice:TextView=view.findViewById(R.id.bookprice)
         val mcardview:CardView=view.findViewById(R.id.dashboardcard)

     }
 }