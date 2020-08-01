package com.example.starsign.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
class DatabaseMonster(
    @PrimaryKey
    override val cardid: Long,
    override val title: String,
    val manarequirements: Map<Mana,Int>,
    val life: Int,
    val attack: Int,
    val defense: Int,
    val magicattack: Int,
    val magicdefense: Int,
    val mp: Int,
    val spells: Map<Spell,Int>?
):DatabaseCard(cardid, title) {}