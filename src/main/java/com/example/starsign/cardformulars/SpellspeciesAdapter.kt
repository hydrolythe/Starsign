package com.example.starsign.cardformulars

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starsign.database.SpellSpecies
import com.example.starsign.databinding.ListItemSpellspeciesBinding

class SpellspeciesAdapter(): ListAdapter<SpellSpecies, RecyclerView.ViewHolder>(SpellDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SpellspeciesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is SpellspeciesViewHolder -> {
                val selectItem = getItem(position) as SpellSpecies
                holder.bind(selectItem)
            }
        }
    }
}

class SpellspeciesViewHolder private constructor(val binding: ListItemSpellspeciesBinding):
    RecyclerView.ViewHolder(binding.root){
    fun bind(item:SpellSpecies){
        binding.spellspecie = item
        binding.executePendingBindings()
    }
    companion object {
        fun from(parent: ViewGroup):SpellspeciesViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemSpellspeciesBinding.inflate(layoutInflater, parent, false)
            return SpellspeciesViewHolder(binding)
        }
    }
}

class SpellDiffCallback: DiffUtil.ItemCallback<SpellSpecies>(){
    override fun areItemsTheSame(oldItem: SpellSpecies, newItem: SpellSpecies): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: SpellSpecies, newItem: SpellSpecies): Boolean {
        return oldItem == newItem
    }
}