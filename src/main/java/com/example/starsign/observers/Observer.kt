package com.example.starsign.observers

import com.example.starsign.database.Card

interface Observer {
    fun update(card:Card)
}