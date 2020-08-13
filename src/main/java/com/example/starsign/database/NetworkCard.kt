package com.example.starsign.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
open class NetworkCard(
    open val cardid: Long,
    open val title: String
): Parcelable {
    fun asDomainModel(): Card {
        return when (this) {
            is NetworkMonster -> Monster(
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
            is NetworkMagic -> Magic(
                title = this.title,
                manaamount = this.manaamount,
                species = this.species,
                spells = this.spells
            )
            is NetworkSource -> Source(title = this.title, manas = this.manas)
            else -> Card(title = this.title)
        }
    }
    fun asDatabaseModel(): DatabaseCard {
        return when (this) {
            is NetworkMonster -> DatabaseMonster(
                monsterid = this.cardid,
                monstertitle = this.title,
                manarequirements = this.manarequirements,
                life = this.life,
                attack = this.attack,
                defense = this.defense,
                magicattack = this.magicattack,
                magicdefense = this.magicdefense,
                mp = this.mp,
                spells = this.spells
            )
            is NetworkMagic -> DatabaseMagic(
                magicid = this.cardid,
                magictitle = this.title,
                manaamount = this.manaamount,
                species = this.species,
                spells = this.spells
            )
            is NetworkSource -> DatabaseSource(sourceid = this.cardid, sourcetitle = this.title, manas = this.manas)
            else -> DatabaseCard(cardid=this.cardid, title=this.title)
        }
    }
}

fun List<NetworkCard>.asDatabaseModel(): List<DatabaseCard>{
    val x = map{
        when(it){
            is NetworkMonster -> DatabaseMonster(monsterid = it.cardid, monstertitle = it.title, manarequirements = it.manarequirements, life = it.life, attack = it.attack, defense = it.defense, magicattack = it.magicattack, magicdefense = it.magicdefense, mp = it.mp, spells = it.spells)
            is NetworkMagic -> DatabaseMagic(magicid = it.cardid, magictitle = it.title, manaamount = it.manaamount, species = it.species, spells = it.spells)
            is NetworkSource -> DatabaseSource(sourceid = it.cardid, sourcetitle = it.title, manas = it.manas)
            else -> DatabaseCard(cardid = it.cardid, title=it.title)
        }
    }
    return x
}