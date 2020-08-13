package com.example.starsign.database

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ProtoMonster(val title: String,
                   val life: Int,
                   val attack: Int,
                   val defense: Int,
                   val magicattack: Int,
                   val magicdefense: Int,
                   val mp: Int):Parcelable