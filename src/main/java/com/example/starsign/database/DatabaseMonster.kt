package com.example.starsign.database

import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Entity(tableName="Monster",inheritSuperIndices = true, indices = arrayOf(Index(value= ["monsterid"]), Index(value = ["monstertitle"], unique=true)),
    foreignKeys = arrayOf(ForeignKey(entity=DatabaseCard::class, parentColumns=arrayOf("cardid"), childColumns = arrayOf("cardid"), onDelete=ForeignKey.CASCADE)))
data class DatabaseMonster(
    @ColumnInfo(name="monsterid")
    val monsterid: Long,
    @ColumnInfo(name="monstertitle")
    val monstertitle: String,
    @ColumnInfo(name="manarequirements")
    val manarequirements: Map<Mana,Int>,
    @ColumnInfo(name="life")
    val life: Int,
    @ColumnInfo(name="attack")
    val attack: Int,
    @ColumnInfo(name="defense")
    val defense: Int,
    @ColumnInfo(name="magicattack")
    val magicattack: Int,
    @ColumnInfo(name="magicdefense")
    val magicdefense: Int,
    @ColumnInfo(name="mp")
    val mp: Int,
    @ColumnInfo(name="spells")
    val spells: Map<Spell,Int>?
):DatabaseCard(monsterid, monstertitle)