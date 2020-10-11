package com.example.starsign.network

import com.example.starsign.database.DatabaseMonster
import com.example.starsign.database.Mana
import com.example.starsign.database.Spell
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkMonster(
    override val cardid: Long,
    override val title: String,
    override val manarequirements: Map<Mana,Int>,
    override val life: Int,
    override val attack: Int,
    override val defense: Int,
    override val magicattack: Int,
    override val magicdefense: Int,
    override val mp: Int,
    override val spells: Map<Spell,Int>?
): NetworkCard("Monster", cardid, title)

fun List<NetworkMonster>.asDatabaseModel(): List<DatabaseMonster>{
    val x = map{
        DatabaseMonster(monsterid = it.cardid, monstertitle = it.title, manarequirements = it.manarequirements, life = it.life, attack = it.attack, defense = it.defense, magicattack = it.magicattack, magicdefense = it.magicdefense, mp = it.mp, spells = it.spells)
    }
    return x
}