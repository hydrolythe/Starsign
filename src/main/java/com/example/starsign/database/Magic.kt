package com.example.starsign.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Spell")
data class Magic(
    @PrimaryKey
    override val title: String,
    @ColumnInfo(name = "species")
    val species: SpellSpecies,
    @ColumnInfo(name = "spells")
    val spells: Map<Spell, Int>
):Card(title) {}