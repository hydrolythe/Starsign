package com.example.starsign.cardcreator

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.adapters.CardViewBindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starsign.R
import com.example.starsign.database.Card
import com.example.starsign.databinding.ListItemCardBinding
import kotlinx.coroutines.*
import org.w3c.dom.Text

class CardCreatorAdapter(val cardListener: CardListener):
    ListAdapter<Card, RecyclerView.ViewHolder>(CardDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder -> {
                val selectedItem = getItem(position) as Card
                holder.bind(selectedItem, cardListener)
            }
        }
    }

    class ViewHolder private constructor(val binding: ListItemCardBinding): RecyclerView.ViewHolder(binding.root){
        var kleurvlag = false
        fun bind(item: Card, clickListener: CardListener){
            binding.card = item
            binding.cardListener = clickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemCardBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class CardListener(val cardListener: (card: Card) -> Unit){
    fun onClick(card:Card) = cardListener(card)
}

class CardDiffCallback: DiffUtil.ItemCallback<Card>(){
    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem.equals(newItem)
    }

    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem.equals(newItem)
    }
}

