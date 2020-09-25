package com.example.starsign.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.ClassCastException
import java.lang.reflect.Type

class NetworkCardDeserializer : JsonDeserializer<NetworkCard> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): NetworkCard {
        when(json?.asJsonObject?.get("type")?.asString){
            "Monster" -> return context?.deserialize<NetworkMonster>(json, NetworkMonster::class.java)!!
            "Magic" -> return context?.deserialize<NetworkMagic>(json, NetworkMagic::class.java)!!
            "Source" -> return context?.deserialize<NetworkSource>(json, NetworkSource::class.java)!!
            else -> throw ClassCastException("Class does not exist yet")
        }
    }
}