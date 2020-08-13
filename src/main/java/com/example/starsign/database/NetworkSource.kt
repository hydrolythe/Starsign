package com.example.starsign.database

import kotlinx.android.parcel.Parcelize

@Parcelize
class NetworkSource(
    override val cardid:Long,
    override val title : String,
    val manas: Map<Mana, Int>
): NetworkCard(cardid, title)