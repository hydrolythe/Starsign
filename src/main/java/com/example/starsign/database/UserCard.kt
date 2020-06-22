package com.example.starsign.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName="Cards_Per_User")
data class UserCard(
    @ColumnInfo(name = "user")
    var user: User,
    @ColumnInfo(name = "card")
    var card: Card
) {
    @PrimaryKey
    var userId : Long = user.userId
    @PrimaryKey
    var cardname : String = card.title
}