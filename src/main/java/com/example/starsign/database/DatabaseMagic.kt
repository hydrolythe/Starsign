package com.example.starsign.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(inheritSuperIndices = true)
class DatabaseMagic(
    override val cardid: Long,
    override val title: String,
    val species: SpellSpecies,
    val spells: Map<Spell, Int>,
    val manaamount: Map<Mana, Int>
):DatabaseCard(cardid, title) {}