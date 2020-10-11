package com.example.starsign.network

import com.example.starsign.database.DatabaseSource
import com.example.starsign.database.Mana
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkSource(
    override val cardid:Long,
    override val title : String,
    override val source: Map<Mana, Int>
): NetworkCard("Source", cardid, title)

fun List<NetworkSource>.asDatabaseModel(): List<DatabaseSource>{
    val x = map{
        DatabaseSource(sourceid = it.cardid, sourcetitle = it.title, manas = it.source)
    }
    return x
}