package com.example.starsign.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class JwtRequest(val username: String, val password: String):Parcelable