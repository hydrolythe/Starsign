package com.example.starsign.database

import androidx.room.Entity
import kotlinx.android.parcel.Parcelize
@Parcelize
@Entity(tableName="Source")
data class Source(
    override val title : String,
    val source: Map<Mana, Int>
):Card(title) {}