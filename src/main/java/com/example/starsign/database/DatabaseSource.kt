package com.example.starsign.database

import androidx.room.Entity
import androidx.room.Index
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(inheritSuperIndices = true)
class DatabaseSource(
    override val cardid:Long,
    override val title : String,
    val manas: Map<Mana, Int>
): DatabaseCard(cardid, title) {}