package com.example.starsign.carddetail

import android.media.effect.Effect
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starsign.cardformulars.EffectDiffCallback
import com.example.starsign.cardformulars.EffectViewHolder
import com.example.starsign.database.Spell
import com.example.starsign.databinding.ItemEffectsBinding

class SpellDetailAdapter(val requirements: Map<Spell, Int>) : ListAdapter<Spell, RecyclerView.ViewHolder>(EffectDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SpellDetailViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is SpellDetailViewHolder -> {
                val selectItem = getItem(position) as Spell
                holder.bind(selectItem, requirements[selectItem]?:0)
            }
        }
    }

}

class SpellDetailViewHolder private constructor(val binding: ItemEffectsBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(item: Spell, number: Int){
        binding.effect = item
        binding.cost = number
    }
    companion object{
        fun from(parent: ViewGroup): SpellDetailViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemEffectsBinding.inflate(layoutInflater, parent, false)
            return SpellDetailViewHolder(binding)
        }
    }
}