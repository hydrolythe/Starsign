package com.example.starsign.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName="user_table")
data class User(
    @PrimaryKey
    var userId : Long = 0L,
    @ColumnInfo(name = "username")
    var username : String,
    @ColumnInfo(name = "password")
    var password : String
){}