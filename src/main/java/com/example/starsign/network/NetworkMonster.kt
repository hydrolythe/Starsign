package com.example.starsign.network

import com.example.starsign.database.DatabaseMonster
import com.example.starsign.database.Mana
import com.example.starsign.database.Spell
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class NetworkMonster(
    @SerializedName("cardid")
    override val cardid: Long,
    @SerializedName("title")
    override val title: String,
    @SerializedName("manarequirements")
    val manarequirements: Map<Mana,Int>,
    @SerializedName("life")
    val life: Int,
    @SerializedName("attack")
    val attack: Int,
    @SerializedName("defense")
    val defense: Int,
    @SerializedName("magicattack")
    val magicattack: Int,
    @SerializedName("magicdefense")
    val magicdefense: Int,
    @SerializedName("mp")
    val mp: Int,
    @SerializedName("spells")
    val spells: Map<Spell,Int>?
): NetworkCard(cardid, title)

fun List<NetworkMonster>.asDatabaseModel(): List<DatabaseMonster>{
    val x = map{
        DatabaseMonster(monsterid = it.cardid, monstertitle = it.title, manarequirements = it.manarequirements, life = it.life, attack = it.attack, defense = it.defense, magicattack = it.magicattack, magicdefense = it.magicdefense, mp = it.mp, spells = it.spells)
    }
    return x
}