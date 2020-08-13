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
import com.example.starsign.databinding.HeaderBinding
import com.example.starsign.databinding.ListItemCardBinding
import kotlinx.coroutines.*
import org.w3c.dom.Text
import java.lang.ClassCastException

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class CardCreatorAdapter(val cardListener: CardListener):
    ListAdapter<DataItem, RecyclerView.ViewHolder>(DataItemDiffCallback()) {
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder -> {
                val selectedItem = getItem(position) as DataItem.CardItem
                holder.bind(selectedItem.card, cardListener)
            }
        }
    }

    fun addHeaderAndSubmitList(list: List<Card>?){
        adapterScope.launch{
            val items = when(list){
                null -> listOf(DataItem.Title)
                else -> listOf(DataItem.Title) + list.map{DataItem.CardItem(it)}
            }
            withContext(Dispatchers.Main){
                submitList(items)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is DataItem.Title -> ITEM_VIEW_TYPE_HEADER
            is DataItem.CardItem -> ITEM_VIEW_TYPE_ITEM
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

class DataItemDiffCallback: DiffUtil.ItemCallback<DataItem>(){
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.equals(newItem)
    }

}

class CardDiffCallback: DiffUtil.ItemCallback<Card>(){
    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem.equals(newItem)
    }

    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem.equals(newItem)
    }
}

sealed class DataItem{
    data class CardItem(val card: Card) : DataItem(){
        override val id = card.title
    }
    object Title: DataItem(){
        override val id = "Cards made"
    }
    abstract val id : String
}

class TextViewHolder private constructor(val binding: HeaderBinding):RecyclerView.ViewHolder(binding.root){
    companion object {
        fun from(parent: ViewGroup): TextViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = HeaderBinding.inflate(layoutInflater, parent, false)
            return TextViewHolder(binding)
        }
    }
}
