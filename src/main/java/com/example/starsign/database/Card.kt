package com.example.starsign.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName="Card")
@Parcelize
open class Card(
    @PrimaryKey
    open val title: String
):Parcelable {}