package com.saitejajanjirala.internshaalaprac2.fragments

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.saitejajanjirala.internshaalaprac2.R
import com.saitejajanjirala.internshaalaprac2.adapters.FavouritesAdapter
import com.saitejajanjirala.internshaalaprac2.databases.BookDatabase
import com.saitejajanjirala.internshaalaprac2.databases.BookEntities
import kotlinx.android.synthetic.main.fragment_favourites.*

class Favourites : Fragment() {
    lateinit var frecycleriew:RecyclerView
    lateinit var fprogressbar:ProgressBar
    lateinit var fprogresslayout:RelativeLayout
    lateinit var flayoutmanager:RecyclerView.LayoutManager
    lateinit var fadapter:FavouritesAdapter
    var dbbooklist= listOf<BookEntities>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View=inflater.inflate(R.layout.fragment_favourites, container, false)
        frecycleriew=view.findViewById(R.id.frecyclerview)
        fprogressbar=view.findViewById(R.id.fprogressbar)
        fprogresslayout=view.findViewById(R.id.progresslayout)
        flayoutmanager=GridLayoutManager(activity as Context,2)
        dbbooklist=RetrieveFavourites(activity as Context).execute().get()
        if(activity!=null){
            fprogresslayout.visibility=View.GONE
            fadapter= FavouritesAdapter(activity as Context,dbbooklist)
            frecycleriew.adapter=fadapter
            frecycleriew.layoutManager=flayoutmanager
        }
        return view
    }
    class RetrieveFavourites(val context:Context):AsyncTask<Void,Void,List<BookEntities>>(){
        override fun doInBackground(vararg p0: Void?): List<BookEntities> {
            val db= Room.databaseBuilder(context,BookDatabase::class.java,"books-db").build()
            return db.bookDao().getallbooks()
        }
    }


}
