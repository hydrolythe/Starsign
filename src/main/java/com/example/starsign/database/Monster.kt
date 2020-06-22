package com.example.starsign.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "monster")
data class Monster(
    @PrimaryKey
    override val title: String,
    @ColumnInfo(name="mana required")
    val manarequirements: Map<Mana,Int>,
    @ColumnInfo(name="life")
    val life: Int,
    @ColumnInfo(name="attack")
    val attack: Int,
    @ColumnInfo(name="defense")
    val defense: Int,
    @ColumnInfo(name="magic attack")
    val magicattack: Int,
    @ColumnInfo(name="magic defense")
    val magicdefense: Int,
    @ColumnInfo(name="magic points")
    val mp: Int,
    @ColumnInfo(name="spells")
    val spells: Map<Spell,Int>
):Card(title) {}