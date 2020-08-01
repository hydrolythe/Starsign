package com.example.starsign.database

import androidx.room.Entity
import androidx.room.Index
import kotlinx.android.parcel.Parcelize

@Parcelize
class DatabaseSource(
    override val cardid:Long,
    override val title : String,
    val source: Map<Mana, Int>
): DatabaseCard(cardid, title) {}