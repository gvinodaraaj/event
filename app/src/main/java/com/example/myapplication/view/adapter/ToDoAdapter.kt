package com.example.myapplication.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.db.model.User
import com.example.myapplication.R
import com.example.myapplication.data.db.model.Event


class ToDoAdapter(private var itemList: MutableList<Event>) : RecyclerView.Adapter<ToDoAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.txtTitle)
        val phoneText: TextView = itemView.findViewById(R.id.txtPlace)
        val emailText: TextView = itemView.findViewById(R.id.txtDesc)
        val dateText: TextView = itemView.findViewById(R.id.txtDate)
        val progress:ProgressBar=itemView.findViewById(R.id.progressBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.todo_list_item, parent, false)
        return ItemViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.nameText.text = currentItem.title
        holder.phoneText.text = currentItem.amount.toString()
        holder.emailText.text = currentItem.place
      //  holder.dateText.text=currentItem.endDate
        holder.progress.setProgress(70,true)
    }

    override fun getItemCount() = itemList.size

}
