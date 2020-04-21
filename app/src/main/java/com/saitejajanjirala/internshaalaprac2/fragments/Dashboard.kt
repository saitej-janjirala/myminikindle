package com.saitejajanjirala.internshaalaprac2.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.security.NetworkSecurityPolicy
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.android.volley.Request
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.saitejajanjirala.internshaalaprac2.R
import com.saitejajanjirala.internshaalaprac2.adapters.dashboardadapter
import com.saitejajanjirala.internshaalaprac2.model.Book
import com.saitejajanjirala.internshaalaprac2.util.Connectivity
import org.json.JSONObject
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class Dashboard : Fragment() {
    lateinit var mrecyclerview:RecyclerView
    lateinit var mlayoutmanager:LinearLayoutManager
    lateinit var madapter: dashboardadapter
    lateinit var bookarraylist:ArrayList<Book>
    lateinit var mprogressbar:ProgressBar
    lateinit var mlayout:RelativeLayout
    val ratingcomparator= Comparator<Book>{book1,book2->
        if(book1.bookRating.compareTo(book2.bookRating,true)==0){
            book1.bookName.compareTo(book2.bookName)
        }
        else{
            book1.bookRating.compareTo(book2.bookRating,true)
        }
    }
    // TODO: Rename and change types of parameters
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        setHasOptionsMenu(true)
        mlayoutmanager= LinearLayoutManager(activity);
        mlayout=view.findViewById(R.id.relativelayout)
        mprogressbar=view.findViewById(R.id.progressbar)
        mrecyclerview=view.findViewById(R.id.dashboardrecyclerview)
            val queue = Volley.newRequestQueue(activity as Context)
            val url = "http://13.235.250.119/v1/book/fetch_books/"
        if(Connectivity().checkconnectivity(activity as Context)) {
            mlayout.visibility=View.VISIBLE
            mrecyclerview.visibility=View.GONE
            val jsonObjectRequest = object :
                JsonObjectRequest(Request.Method.GET, url, null, Response.Listener<JSONObject> {
                    try {
                        val success = it.getBoolean("success")
                        if (success) {
                            bookarraylist = ArrayList()
                            val data = it.getJSONArray("data")
                            Log.d("size", data.length().toString())
                            for (i in 0..data.length() - 1) {
                                val bookjsonobject = data.getJSONObject(i)
                                val bookobject = Book(
                                    bookjsonobject.getString("book_id"),
                                    bookjsonobject.getString("name"),
                                    bookjsonobject.getString("author"),
                                    bookjsonobject.getString("rating"),
                                    bookjsonobject.getString("price"),
                                    bookjsonobject.getString("image")
                                )
                                bookarraylist.add(bookobject)
                            }
                            Log.d("list", "$bookarraylist")
                            madapter = dashboardadapter(activity as Context, bookarraylist)
                            mrecyclerview.layoutManager = mlayoutmanager
                            mrecyclerview.adapter = madapter
                            mlayout.visibility=View.GONE
                            mrecyclerview.visibility=View.VISIBLE
                        } else {
                            if(activity!=null) {
                                Toast.makeText(
                                    activity as Context,
                                    "Failed to load",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                        }
                    }
                    catch (e:Exception){
                        if(activity!=null) {
                            Toast.makeText(
                                activity as Context,
                                "Unexpected error occured",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }

                    }

                }
                    , Response.ErrorListener {
                        if(activity!=null) {
                            Toast.makeText(
                                activity as Context,
                                "Error occured try again later",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "0f768ec1585de2"
                    return headers
                }
            }
            queue.add(jsonObjectRequest)
        }
        else{
            val dialog=AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection is not found")
            dialog.setPositiveButton("Open settings"){text,listener->
                val intent= Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(intent)
                activity?.finish()
            }
            dialog.setNegativeButton("Exit"){text,listener->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()

        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if(id===R.id.sortmenuitem){
            Collections.sort(bookarraylist,ratingcomparator)
            bookarraylist.reverse()
        }
        madapter.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)
    }

}
