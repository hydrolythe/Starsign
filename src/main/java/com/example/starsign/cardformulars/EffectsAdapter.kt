package com.example.starsign.cardformulars

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starsign.database.Spell
import com.example.starsign.databinding.ListItemEffectsBinding

class EffectsAdapter(): ListAdapter<Spell, RecyclerView.ViewHolder>(EffectDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EffectViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is EffectViewHolder -> {
                val selectItem = getItem(position) as Spell
                holder.bind(selectItem)
            }
        }
    }
}

class EffectViewHolder private constructor(val binding: ListItemEffectsBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(item: Spell){
        binding.spelleffect = item
        binding.executePendingBindings()
    }
    companion object {
        fun from(parent: ViewGroup): EffectViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemEffectsBinding.inflate(layoutInflater, parent, false)
            return EffectViewHolder(binding)
        }
    }
}

class EffectDiffCallback: DiffUtil.ItemCallback<Spell>(){
    override fun areItemsTheSame(oldItem: Spell, newItem: Spell): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Spell, newItem: Spell): Boolean {
        return oldItem == newItem
    }
}