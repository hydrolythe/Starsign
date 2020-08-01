package com.example.starsign.database

import androidx.room.Entity
import kotlinx.android.parcel.Parcelize
@Parcelize
data class Source(
    override val title : String,
    val manas: Map<Mana, Int>
):Card(title) {}