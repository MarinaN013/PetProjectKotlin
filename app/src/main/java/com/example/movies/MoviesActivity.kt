package com.example.movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        //onBackPressedDispatcher.onBackPressed()
        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)//инициализация recycleView

        // this creates a vertical layout Manager
        recyclerview.layoutManager = GridLayoutManager(this, 2)//инициализация recycleView= вертикальный список

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()//список для тестов

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..20) {
            data.add(ItemsViewModel(com.firebase.ui.auth.R.drawable.common_full_open_on_phone, "Item " + i))//наполнили список значениями
        }



        val apiInterface = ApiInterface.create().getMovies("701c5c2b1967622813c0e8d064360a45")//getMovies(R.string.api_key.toString())//getString(R.string.api_key).

        //apiInterface.enqueue( Callback<List<Movie>>())
        apiInterface.enqueue( object : Callback<Movies>, CustomAdapter.ItemClickListener {
            override fun onResponse(call: Call<Movies> ?, response: Response<Movies> ?) {
                Log.d("testLogs", "OnResponce Success ${call.toString()} ${response?.toString()}")
                // This will pass the ArrayList to our Adapter
                val adapter = CustomAdapter(response?.body()?.results, this)//инициализация класса адаптер
                // Setting the Adapter with the recyclerview
                recyclerview.adapter = adapter//инициализация recycleView=адаптер


//                if(response?.body() != null)
//                    recyclerAdapter.setMovieListItems(response.body()!!)
            }

            override fun onFailure(call: Call<Movies>?, t: Throwable?) {
                Log.d("testLogs", "OnResponce unSuccess ${t?.message}")

            }

            override fun onItemClick(id: Int) {
                //Toast.makeText(this@MoviesActivity, "click ${position}", Toast.LENGTH_LONG ).show()
                val intent = Intent(this@MoviesActivity,MoviesDetailsActivity::class.java)
                intent.putExtra("id", id)//ключ, значение
                startActivity(intent)
            }
        })

    }
    override fun onBackPressed() {
        super.onBackPressed()
        this.finishAffinity()
    }

}



