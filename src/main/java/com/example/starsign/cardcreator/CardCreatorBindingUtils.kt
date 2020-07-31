package com.example.starsign.cardcreator

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.starsign.database.Card
import com.example.starsign.database.Magic
import com.example.starsign.database.Monster
import com.example.starsign.database.Source

@BindingAdapter("typeloader")
fun TextView.setClassType(card: Card){
    card.let{
        when(it){
            is Monster -> text = "Monster"
            is Magic -> text = "Magic"
            is Source -> text = "Source"
        }
    }
}