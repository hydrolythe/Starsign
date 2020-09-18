package com.example.starsign.database

import androidx.room.*
import kotlinx.android.parcel.Parcelize


@Entity(tableName="Magic", inheritSuperIndices = true, indices = arrayOf(Index(value= ["magicid"]), Index(value = ["magictitle"], unique=true)),
        foreignKeys = arrayOf(ForeignKey(entity=DatabaseCard::class, parentColumns=arrayOf("cardid"), childColumns = arrayOf("cardid"), onDelete=ForeignKey.CASCADE)))
data class DatabaseMagic(
    @ColumnInfo(name="magicid")
    val magicid: Long,
    @ColumnInfo(name="magictitle")
    val magictitle: String,
    @ColumnInfo(name="species")
    val species: SpellSpecies,
    @ColumnInfo(name="spells")
    val spells: Map<Spell, Int>,
    @ColumnInfo(name="manaamount")
    val manaamount: Map<Mana, Int>
):DatabaseCard(magicid, magictitle)