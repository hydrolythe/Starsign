package com.example.starsign.cardcreator

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.starsign.database.Card

@BindingAdapter("typeloader")
fun TextView.setClassType(card: Card){
    card.let{
        text = card.javaClass.name
    }
}