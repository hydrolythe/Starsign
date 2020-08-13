package com.example.starsign.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class JwtResponse(val jwtToken: String): Parcelable