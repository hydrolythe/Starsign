package com.example.starsign.cardformulars

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starsign.database.Mana
import com.example.starsign.databinding.ListItemManaBinding

class AttributeAdapter(): ListAdapter<Mana, RecyclerView.ViewHolder>(AttributeDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AttributeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is AttributeViewHolder -> {
                val selectItem = getItem(position) as Mana
                holder.bind(selectItem)
            }
        }
    }
}

class AttributeViewHolder private constructor(val binding: ListItemManaBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(item:Mana){
        binding.spellattribute = item
        binding.executePendingBindings()
    }
    companion object{
        fun from(parent: ViewGroup):AttributeViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemManaBinding.inflate(layoutInflater, parent, false)
            return AttributeViewHolder(binding)
        }
    }
}

class AttributeDiffCallback : DiffUtil.ItemCallback<Mana>(){
    override fun areItemsTheSame(oldItem: Mana, newItem: Mana): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Mana, newItem: Mana): Boolean {
        return oldItem == newItem
    }
}