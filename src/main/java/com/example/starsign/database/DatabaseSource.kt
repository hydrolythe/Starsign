package com.example.starsign.database

import androidx.room.*
import kotlinx.android.parcel.Parcelize


@Entity(tableName="Source", inheritSuperIndices = true, indices = arrayOf(Index(value= ["sourceid"]), Index(value = ["sourcetitle"], unique=true)),
    foreignKeys = arrayOf(ForeignKey(entity=DatabaseCard::class, parentColumns=arrayOf("cardid"), childColumns = arrayOf("cardid"), onDelete= ForeignKey.CASCADE)))
data class DatabaseSource(
    @ColumnInfo(name="sourceid")
    val sourceid:Long,
    @ColumnInfo(name="sourcetitle")
    val sourcetitle : String,
    @ColumnInfo(name="manas")
    val manas: Map<Mana, Int>
): DatabaseCard(sourceid, sourcetitle)