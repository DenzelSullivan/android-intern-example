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

class CategoryAdapter(
    private val data: List<Category>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as DataViewHolder
        holder.bind(data[position], listener)
    }
}

class DataViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val textView: TextView = view.firstTextView
    private val imageView: ImageView = view.imageView

    fun bind(data: Category, listener: OnItemClickListener){
        textView.text = data.name
        loadImage(data.imageUrl)
        view.setOnClickListener { listener.onItemClick(data) }
    }

    private fun loadImage(url: String){
        Picasso.get().load(url).into(imageView)
    }
}

interface OnItemClickListener {
    fun onItemClick(data: Category)
}