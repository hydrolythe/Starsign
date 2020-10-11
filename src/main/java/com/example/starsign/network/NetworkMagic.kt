package com.example.starsign.network

import com.example.starsign.database.DatabaseMagic
import com.example.starsign.database.Mana
import com.example.starsign.database.Spell
import com.example.starsign.database.SpellSpecies
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkMagic(
    override val cardid: Long,
    override val title: String,
    override val species: SpellSpecies,
    override val spells: Map<Spell, Int>,
    override val cost: Map<Mana, Int>
): NetworkCard("Magic", cardid, title)

fun List<NetworkMagic>.asDatabaseModel(): List<DatabaseMagic>{
    val x = map{
        DatabaseMagic(magicid = it.cardid, magictitle = it.title, manaamount = it.cost, species = it.species, spells = it.spells)
    }
    return x
}