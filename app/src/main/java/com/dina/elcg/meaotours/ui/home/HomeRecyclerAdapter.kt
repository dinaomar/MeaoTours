package com.dina.elcg.meaotours.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dina.elcg.meaotours.R
import com.dina.elcg.meaotours.loadImage
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.android.synthetic.main.home_item.view.*


class HomeRecyclerAdapter(val query: ArrayList<QueryDocumentSnapshot>, val listner: ClickListner) :
    RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>() {

    interface ClickListner {
        fun onClick(item: QueryDocumentSnapshot)
    }

    class HomeViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val imageView = view.image
        val title = view.title
        val description = view.description
        fun bind(
            item: QueryDocumentSnapshot,
            listner: ClickListner
        ) {
            title.text = item.data["title"].toString()
            description.text = item.data["description"].toString()
            imageView.loadImage(item.data["image"])

            view.setOnClickListener {
                listner.onClick(item)
            }
        }
    }

    fun updateUsers(newUsers: ArrayList<QueryDocumentSnapshot>) {
        query.clear()
        query.addAll(newUsers)
        notifyDataSetChanged()
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
        return query.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(query[position], listner)
    }
}