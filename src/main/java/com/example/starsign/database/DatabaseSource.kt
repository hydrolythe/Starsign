package com.example.starsign.database

import androidx.room.*
import kotlinx.android.parcel.Parcelize


@Entity(tableName="Source", inheritSuperIndices = true, indices = arrayOf(Index(value= ["sourceid"]), Index(value = ["sourcetitle"], unique=true)))
data class DatabaseSource(
    @PrimaryKey
    val sourceid:Long,
    @ColumnInfo(name="sourcetitle")
    val sourcetitle : String,
    @ColumnInfo(name="manas")
    val manas: Map<Mana, Int>
): DatabaseCard(sourceid, sourcetitle)