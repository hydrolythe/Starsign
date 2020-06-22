package com.example.starsign.cardcreator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.adapters.CardViewBindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starsign.R
import com.example.starsign.database.Card
import com.example.starsign.databinding.ListItemCardBinding
import kotlinx.coroutines.*
import org.w3c.dom.Text

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1
class CardCreatorAdapter(val cardListener: CardListener):
    ListAdapter<DataItem, RecyclerView.ViewHolder>(CardDiffCallback()) {
    private val adapterScope = CoroutineScope(Dispatchers.Default)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unkown viewType ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder -> {
                val selectItem = getItem(position) as DataItem.CardItem
                holder.bind(selectItem.card, cardListener)
            }
        }
    }

    class ViewHolder private constructor(val binding: ListItemCardBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Card, clickListener: CardListener){
            binding.card = item
            binding.cardListener = clickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                var binding = ListItemCardBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    fun addHeaderAndSubmitList(list: List<Card>){
        adapterScope.launch{
            var items = when(list){
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map{DataItem.CardItem(it)}
            }
            withContext(Dispatchers.Main){
                submitList(items)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.CardItem -> ITEM_VIEW_TYPE_ITEM
        }
    }
}

class CardListener(val cardListener: (cardName: String) -> Unit){
    fun onClick(card:Card) = cardListener(card.title)
}

sealed class DataItem{
    abstract val id: String
    data class CardItem(val card:Card) : DataItem(){
        override val id = card.title
    }
    object Header: DataItem(){
        override val id = "Cards"
    }
}

class CardDiffCallback: DiffUtil.ItemCallback<DataItem>(){
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id.equals(newItem.id)
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.equals(newItem)
    }
}

class TextViewHolder(view: View): RecyclerView.ViewHolder(view){
    companion object{
        fun from(parent: ViewGroup): TextViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.header, parent, false)
            return TextViewHolder(view)
        }
    }
}