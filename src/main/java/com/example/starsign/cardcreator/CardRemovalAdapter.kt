package com.example.starsign.cardcreator

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starsign.database.Card

class CardRemovalAdapter(val cardListener: CardListener):
    ListAdapter<Card, RecyclerView.ViewHolder>(CardDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CardCreatorAdapter.ViewHolder.from(parent)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is CardCreatorAdapter.ViewHolder -> {
                val selectedItem = getItem(position) as Card
                holder.bind(selectedItem, cardListener)
                holder.itemView.setOnClickListener{
                    if (!holder.kleurvlag) {
                        it.background = ColorDrawable(Color.parseColor("#FC7F31"))
                        holder.kleurvlag = true
                    } else {
                        it.background = ColorDrawable(Color.WHITE)
                        holder.kleurvlag = false
                    }
                }
            }
        }
    }

}