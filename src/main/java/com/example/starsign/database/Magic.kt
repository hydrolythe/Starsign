package com.example.starsign.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Magic(
    override val title: String,
    val species: SpellSpecies,
    val spells: Map<Spell, Int>,
    val manaamount: Map<Mana, Int>
):Card(title) {}