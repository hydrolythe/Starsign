package com.example.starsign.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDto(val username:String, val password:String, val matchingPassword:String):
    Parcelable {
}