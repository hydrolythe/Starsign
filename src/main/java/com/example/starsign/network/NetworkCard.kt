package com.example.starsign.network

import android.os.Parcelable
import com.example.starsign.database.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
open class NetworkCard(
    @Json(name = "type")
    val type: String,
    open val cardid: Long,
    open val title: String,
    open val manarequirements: Map<Mana,Int>?=null,
    open val life: Int=0,
    open val attack: Int=0,
    open val defense: Int=0,
    open val magicattack: Int=0,
    open val magicdefense: Int=0,
    open val mp: Int=0,
    open val spells: Map<Spell,Int>?=null,
    open val species: SpellSpecies = SpellSpecies.EVENT,
    open val cost: Map<Mana, Int>?=null,
    open val source: Map<Mana, Int>?=null
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
                manaamount = this.cost,
                species = this.species,
                spells = this.spells
            )
            is NetworkSource -> Source(title = this.title, manas = this.source)
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
                manaamount = this.cost,
                species = this.species,
                spells = this.spells
            )
            is NetworkSource -> DatabaseSource(sourceid = this.cardid, sourcetitle = this.title, manas = this.source)
            else -> DatabaseCard(cardid=this.cardid, title=this.title)
        }
    }
}

fun List<NetworkCard>.asDatabaseModel(): List<DatabaseCard>{
    val x = map{
        when(it){
            is NetworkMonster -> DatabaseMonster(monsterid = it.cardid, monstertitle = it.title, manarequirements = it.manarequirements, life = it.life, attack = it.attack, defense = it.defense, magicattack = it.magicattack, magicdefense = it.magicdefense, mp = it.mp, spells = it.spells)
            is NetworkMagic -> DatabaseMagic(magicid = it.cardid, magictitle = it.title, manaamount = it.cost, species = it.species, spells = it.spells)
            is NetworkSource -> DatabaseSource(sourceid = it.cardid, sourcetitle = it.title, manas = it.source)
            else -> DatabaseCard(cardid = it.cardid, title=it.title)
        }
    }
    return x
}