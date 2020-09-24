package com.example.starsign.network

import com.example.starsign.database.DatabaseSource
import com.example.starsign.database.Mana
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class NetworkSource(
    @SerializedName("cardid")
    override val cardid:Long,
    @SerializedName("title")
    override val title : String,
    @SerializedName("source")
    val manas: Map<Mana, Int>
): NetworkCard(cardid, title)

fun List<NetworkSource>.asDatabaseModel(): List<DatabaseSource>{
    val x = map{
        DatabaseSource(sourceid = it.cardid, sourcetitle = it.title, manas = it.manas)
    }
    return x
}