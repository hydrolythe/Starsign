package com.example.starsign.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
class DatabaseMagic(
    @PrimaryKey
    override val cardid: Int,
    override val title: String,
    val species: SpellSpecies,
    val spells: Map<Spell, Int>,
    val manaamount: Map<Mana, Int>
):DatabaseCard(cardid, title) {}