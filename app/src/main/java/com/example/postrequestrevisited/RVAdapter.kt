package com.example.postrequestrevisited
import android.content.Context

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.postrequestrevisited.databinding.ItemRowBinding



class RVAdapter(private val users: Users ,val context:Context): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val name=users.get(position).name
        val location=users.get(position).location
        val id=users.get(position).pk
        holder.binding.apply {
            tvName.text = name
            tvLocation.text = location
            tvId.text = "User ID: "+id
        }

        }

    override fun getItemCount()= users.size

}