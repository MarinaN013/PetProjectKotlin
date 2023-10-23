package com.example.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R.layout.card_view_design
import com.squareup.picasso.Picasso

class CustomAdapter(private val mList: List<Result>?,
                    val mItemClickListener:ItemClickListener) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    interface ItemClickListener{
        fun onItemClick(position: Int)//position
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        //создается ячейка1
        val view = LayoutInflater.from(parent.context)
            .inflate(card_view_design, parent, false)//наш xml файл
        return ViewHolder(view)
    }

    // binds the list items to a view
    //манипуляци с ячейкой3
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList?.get(position)

        Picasso.get().load("https://image.tmdb.org/t/p/w500" + mList?.get(position)?.poster_path).into(holder.imageView)

        // sets the image to the imageview from our itemHolder class
        //holder.imageView.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class
        //holder.textView.text = ItemsViewModel?.title

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList!!.size
    }

    // Holds the views for adding it to image and text
    //инициализируется ячейка2
    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        //val textView: TextView = itemView.findViewById(R.id.textView)
        init{
            ItemView.setOnClickListener{
                mList?.get(position)?.id?.let { it -> mItemClickListener.onItemClick(it)}
            }
        }
    }
}