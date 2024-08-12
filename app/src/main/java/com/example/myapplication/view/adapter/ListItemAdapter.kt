package com.example.myapplication.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.db.model.User
import com.example.myapplication.R


class ItemAdapter(private var itemList: MutableList<User>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.text_name)
        val phoneText: TextView = itemView.findViewById(R.id.text_phone)
        val emailText: TextView = itemView.findViewById(R.id.text_email)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.nameText.text = currentItem.name
        holder.phoneText.text = currentItem.phone
        holder.emailText.text = currentItem.mail
    }

    override fun getItemCount() = itemList.size

    fun updateList(newItemList: List<User>) {
        itemList.clear()
        itemList.addAll(newItemList)
        notifyDataSetChanged()
    }
}
