package com.dvargas.adaschool.cornershop.dataLayer.dto

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dvargas.adaschool.cornershop.databinding.ItemViewBinding

class ItemAdapter(private val items: List<Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class ViewHolder(private val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            binding.itemName.text = item.name
            binding.itemDescription.text = item.description
            Glide.with(binding.root.context).load(item.image).into(binding.itemImage)
        }
    }

}