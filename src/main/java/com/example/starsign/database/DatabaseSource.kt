package com.example.starsign.database

import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
class DatabaseSource(
    override val cardid:Int,
    override val title : String,
    val source: Map<Mana, Int>
): DatabaseCard(cardid, title) {}