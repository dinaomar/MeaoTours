package com.dina.elcg.meaotours.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dina.elcg.meaotours.R
import com.dina.elcg.meaotours.loadImage
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.home_item.view.*


class HomeRecyclerAdapter(private val query: QuerySnapshot) :
    RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>() {

    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = view.image
        val title = view.title
        val description = view.description

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.home_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return query.size()
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.imageView.loadImage(query.documents[position].data!!["image"])
        holder.description.text = query.documents[position].data!!["description"].toString()
        holder.title.text = query.documents[position].data!!["title"].toString()
    }
}