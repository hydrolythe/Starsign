package com.example.starsign.database

import kotlinx.android.parcel.Parcelize

@Parcelize
class NetworkMagic(
    override val cardid: Long,
    override val title: String,
    val species: SpellSpecies,
    val spells: Map<Spell, Int>,
    val manaamount: Map<Mana, Int>
):NetworkCard(cardid, title)