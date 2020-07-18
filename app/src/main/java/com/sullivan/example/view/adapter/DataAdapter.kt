package com.sullivan.example.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sullivan.example.R
import com.sullivan.example.model.data.Category
import kotlinx.android.synthetic.main.item_data.view.*

class DataAdapter(
    private val data: List<Category>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as DataViewHolder
        holder.textView.text = data[position].name
        holder.loadImage(data[position].imageUrl)
    }

}

class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView: TextView = view.firstTextView
    private val imageView: ImageView = view.imageView

    fun loadImage(url: String){
        Picasso
            .get()
            .load(url)
            .into(imageView)
    }
}