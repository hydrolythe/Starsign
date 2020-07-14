package com.example.starsign.carddetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starsign.cardformulars.AttributeDiffCallback
import com.example.starsign.cardformulars.AttributeViewHolder
import com.example.starsign.database.Mana
import com.example.starsign.databinding.ItemManaBinding
import com.example.starsign.databinding.ListItemManaBinding

class ManaDetailAdapter(val requirements: Map<Mana, Int>): ListAdapter<Mana, RecyclerView.ViewHolder>(AttributeDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ManaDetailViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ManaDetailViewHolder -> {
                val selectItem = getItem(position) as Mana
                holder.bind(selectItem, requirements[selectItem]?:0)
            }
        }
    }

}

class ManaDetailViewHolder private constructor(val binding: ItemManaBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(item:Mana, number:Int){
        binding.mana = item
        binding.amount = number
    }
    companion object{
        fun from(parent: ViewGroup): ManaDetailViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemManaBinding.inflate(layoutInflater, parent, false)
            return ManaDetailViewHolder(binding)
        }
    }
}