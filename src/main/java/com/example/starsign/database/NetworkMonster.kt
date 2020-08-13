package com.example.starsign.database

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class NetworkMonster(
    override val cardid: Long,
    override val title: String,
    val manarequirements: Map<Mana,Int>,
    val life: Int,
    val attack: Int,
    val defense: Int,
    val magicattack: Int,
    val magicdefense: Int,
    val mp: Int,
    val spells: Map<Spell,Int>?
):NetworkCard(cardid, title)