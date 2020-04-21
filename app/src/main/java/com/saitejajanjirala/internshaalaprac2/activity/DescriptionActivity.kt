package com.saitejajanjirala.internshaalaprac2.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.saitejajanjirala.internshaalaprac2.R
import org.json.JSONObject
import com.android.volley.Request
import com.android.volley.Response
import com.saitejajanjirala.internshaalaprac2.databases.BookDatabase
import com.saitejajanjirala.internshaalaprac2.databases.BookEntities
import com.saitejajanjirala.internshaalaprac2.util.Connectivity
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class DescriptionActivity : AppCompatActivity() {
    lateinit var bookname:TextView
    lateinit var authorname:TextView
    lateinit var bookprice:TextView
    lateinit var bookrating:TextView
    lateinit var bookimage:ImageView
    lateinit var mlayout:RelativeLayout
    lateinit var mprogressbar:ProgressBar
    lateinit var mmainlayout:RelativeLayout
    lateinit var addtofavourite:Button
    lateinit var bookdescription:TextView
    lateinit var toolbar: Toolbar
    var bookid:String?="100"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        bookname=findViewById(R.id.mbookname)
        authorname=findViewById(R.id.mauthorname)
        bookprice=findViewById(R.id.mbookprice)
        bookrating=findViewById(R.id.mbookrating)
        bookimage=findViewById(R.id.mbookimage)
        mlayout=findViewById(R.id.mrelativelayout)
        mprogressbar=findViewById(R.id.mprogressbar)
        mmainlayout=findViewById(R.id.mmainlayout)
        bookdescription=findViewById(R.id.bookdescription)
        addtofavourite=findViewById(R.id.addtofavourite)
        toolbar=findViewById(R.id.mtoolbar)
        settoolbar()
        if(intent!=null){
            bookid=intent.getStringExtra("bookid")
        }
        else{
            finish()
            Toast.makeText(this@DescriptionActivity,"Some unexpected error occured",Toast.LENGTH_LONG).show()
        }

        if(bookid=="100"){
            finish()
            Toast.makeText(this@DescriptionActivity,"Some unexpected error occured",Toast.LENGTH_LONG).show()
        }
        mmainlayout.visibility=View.GONE
        val queue=Volley.newRequestQueue(this@DescriptionActivity)
        val url="http://13.235.250.119/v1/book/get_book/"
        val jsonparams=JSONObject()
        jsonparams.put("book_id",bookid)
        if(Connectivity().checkconnectivity(this@DescriptionActivity)) {
            mlayout.visibility=View.VISIBLE
            mprogressbar.visibility=View.VISIBLE
            val jsonrequest = object : JsonObjectRequest(Request.Method.POST, url, jsonparams,
                Response.Listener<JSONObject> {
                    try {
                        val success=it.getBoolean("success")
                        if(success) {
                            val jsonobject = it.getJSONObject("book_data")
                            mmainlayout.visibility = View.VISIBLE
                            mprogressbar.visibility = View.GONE
                            mlayout.visibility = View.GONE
                            Picasso.get().load(jsonobject.getString("image"))
                                .error(R.drawable.defaultbookcover).into(bookimage)
                            bookname.setText(jsonobject.getString("name"))
                            authorname.setText(jsonobject.getString("author"))
                            bookprice.setText(jsonobject.getString("price"))
                            bookrating.setText(jsonobject.getString("rating"))
                            bookdescription.setText(jsonobject.getString("description"))
                            val bookEntities=BookEntities(
                                 bookid?.toInt() as Int,
                                bookname.text.toString(),
                                authorname.text.toString(),
                                bookprice.text.toString(),
                                bookrating.text.toString(),
                                jsonobject.getString("image"),
                                bookdescription.text.toString()
                            )
                            val checkfav=DBAsynctask(this@DescriptionActivity,bookEntities,1).execute()
                            val isfav=checkfav.get()
                            if(isfav){
                                addtofavourite.text="Remove from favourites"
                                addtofavourite.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.authorname))
                            }
                            else{
                                addtofavourite.text="Add to favourites"
                                addtofavourite.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.colorPrimary))

                            }
                            addtofavourite.setOnClickListener {
                                if(!DBAsynctask(this@DescriptionActivity,bookEntities,1).execute().get()){
                                    if(DBAsynctask(this@DescriptionActivity,bookEntities,2).execute().get()){
                                        Toast.makeText(this@DescriptionActivity,"Added to Favourites",Toast.LENGTH_SHORT).show()
                                        addtofavourite.text="Remove from favourites"
                                        addtofavourite.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.authorname))
                                    }
                                    else{
                                        Toast.makeText(this@DescriptionActivity,"Some error occured",Toast.LENGTH_SHORT).show()
                                    }

                                }
                                else{
                                    if(DBAsynctask(this@DescriptionActivity,bookEntities,3).execute().get()){
                                        Toast.makeText(this@DescriptionActivity,"Removed from favourites",Toast.LENGTH_SHORT).show()
                                        addtofavourite.text="Add to favourites"
                                        addtofavourite.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
                                    }else
                                    {
                                        Toast.makeText(this@DescriptionActivity,"Some error occured",Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }

                        }
                        else{
                            Toast.makeText(this@DescriptionActivity,"Failed to load",Toast.LENGTH_LONG).show()
                        }

                    } catch (e: Exception) {
                        Toast.makeText(this@DescriptionActivity,"Some unexpected error occured",Toast.LENGTH_LONG).show()
                    }
                },
                Response.ErrorListener {
                    Toast.makeText(this@DescriptionActivity,"Some unexpected error $it",Toast.LENGTH_LONG).show()

                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "0f768ec1585de2"
                    return headers
                }
            }
            queue.add(jsonrequest)
        }
        else{
            val dialog= AlertDialog.Builder(this@DescriptionActivity)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection is not found")
            dialog.setPositiveButton("Open settings"){text,listener->
                val intent= Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(intent)
                finish()
            }
            dialog.setNegativeButton("Exit"){text,listener->
                ActivityCompat.finishAffinity(this@DescriptionActivity)
            }
            dialog.create()
            dialog.show()

        }
    }
    fun settoolbar()
    {
            setSupportActionBar(toolbar)
            supportActionBar?.title="Book Details"

    }
    class DBAsynctask(val context:Context,val bookEntities: BookEntities,val mode:Int):AsyncTask<Void,Void,Boolean>(){
        /*
        mode1->check db if the book is favourite or not
       mode2->save the book into db as favourite
       mode3->remove the favourite book
         */
        val db=Room.databaseBuilder(context,BookDatabase::class.java,"books-db").build()

        override fun doInBackground(vararg p0: Void?): Boolean {
            when(mode){
                1->{
                    val books:BookEntities?=db.bookDao().getbookbyid(bookEntities.book_id.toString())
                    db.close()
                    return books!=null
                }
                2->{
                    db.bookDao().insertbook(bookEntities)
                    db.close()
                    return true
                }
                3->{
                        db.bookDao().deletebook(bookEntities)
                        db.close()
                        return true
                }
            }
            return false
            //To change body of created functions use File | Settings | File Templates.
        }

    }

}
