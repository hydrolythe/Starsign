package com.example.starsign.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName="Card", indices = arrayOf(Index(value = ["title"], unique=true)))
open class DatabaseCard(
    @PrimaryKey
    open val cardid: Int,
    @ColumnInfo(name="title")
    open val title: String
): Parcelable {
    fun asDomainModel(): Card {
        return when (this) {
            is DatabaseMonster -> Monster(
                title = this.title,
                manarequirements = this.manarequirements,
                life = this.life,
                attack = this.attack,
                defense = this.defense,
                magicattack = this.magicattack,
                magicdefense = this.magicdefense,
                mp = this.mp,
                spells = this.spells
            )
            is DatabaseMagic -> Magic(
                title = this.title,
                manaamount = this.manaamount,
                species = this.species,
                spells = this.spells
            )
            is DatabaseSource -> Source(title = this.title, source = this.source)
            else -> throw IllegalArgumentException("The type can not be converted")
        }
    }
}

fun List<DatabaseCard>.asDomainModel(): List<Card>{
    return map{
        when(it){
            is DatabaseMonster -> Monster(title = it.title, manarequirements = it.manarequirements, life = it.life, attack = it.attack, defense = it.defense, magicattack = it.magicattack, magicdefense = it.magicdefense, mp = it.mp, spells = it.spells)
            is DatabaseMagic -> Magic(title = it.title, manaamount = it.manaamount, species = it.species, spells = it.spells)
            is DatabaseSource -> Source(title = it.title, source = it.source)
            else -> throw IllegalArgumentException("The type can not be converted")
        }
    }
}