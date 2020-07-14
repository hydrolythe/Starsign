package com.example.starsign.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Monster(
    override val title: String,
    val manarequirements: Map<Mana,Int>,
    val life: Int,
    val attack: Int,
    val defense: Int,
    val magicattack: Int,
    val magicdefense: Int,
    val mp: Int,
    val spells: Map<Spell,Int>
):Card(title) {}