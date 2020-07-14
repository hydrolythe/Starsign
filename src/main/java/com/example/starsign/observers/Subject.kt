package com.example.starsign.observers

import com.example.starsign.database.Card

interface Subject {
    fun registerObserver(o:Observer)
    fun notifyObservers(card: Card)
    fun removeObserver(o:Observer)
}