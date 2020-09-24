package com.example.starsign.network

import com.example.starsign.database.DatabaseMagic
import com.example.starsign.database.Mana
import com.example.starsign.database.Spell
import com.example.starsign.database.SpellSpecies
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class NetworkMagic(
    @SerializedName("cardid")
    override val cardid: Long,
    @SerializedName("title")
    override val title: String,
    @SerializedName("species")
    val species: SpellSpecies,
    @SerializedName("spells")
    val spells: Map<Spell, Int>,
    @SerializedName("cost")
    val manaamount: Map<Mana, Int>
): NetworkCard(cardid, title)

fun List<NetworkMagic>.asDatabaseModel(): List<DatabaseMagic>{
    val x = map{
        DatabaseMagic(magicid = it.cardid, magictitle = it.title, manaamount = it.manaamount, species = it.species, spells = it.spells)
    }
    return x
}