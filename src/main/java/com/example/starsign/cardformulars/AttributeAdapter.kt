package com.example.starsign.cardformulars

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starsign.database.Mana
import com.example.starsign.databinding.ListItemManaBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AttributeAdapter(val manalist: Map<Mana,Int>?=null): ListAdapter<Mana, RecyclerView.ViewHolder>(AttributeDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AttributeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is AttributeViewHolder -> {
                val selectItem = getItem(position) as Mana
                if(manalist!=null) {
                    if (manalist.containsKey(selectItem)){
                        holder.bind(selectItem, manalist.get(selectItem)!!)
                    }
                    else{
                        holder.bind(selectItem)
                    }
                }
                else {
                    holder.bind(selectItem)
                }
            }
        }
    }
}

class AttributeViewHolder private constructor(val binding: ListItemManaBinding):RecyclerView.ViewHolder(binding.root){

    fun bind(item:Mana, amount:Int = 0){
        binding.spellattribute = item
        binding.amount = amount
        binding.executePendingBindings()
    }

    fun getMana(): Mana?{
        return binding.spellattribute
    }
    fun cleartext(){
        binding.amount = 0
    }
    fun getManaAmount(): Int{
        return Integer.parseInt(binding.manaamounttext.text.toString())
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