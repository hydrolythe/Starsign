package com.example.starsign

import android.os.Build
import androidx.annotation.RequiresApi
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.*

object JwtUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getExpiracyDate(token: String): Date {
        try {
            val substitute = token.replace('-','+').replace('_','/')
            val split : List<String> =
                substitute.split(".")
            val total : String = getJson(split[1])
            val jsonTotal = JSONObject(total)
            val exp : Any = jsonTotal.get("exp")
            val long: Long = Integer.parseInt(exp.toString())*1000L
            return Date(long)
        } catch (e: UnsupportedEncodingException) {
            throw Exception(e)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getJson(strEncoded: String): String {
        val decoder = Base64.getDecoder()
        val decodedBytes = decoder.decode(strEncoded)
        return String(decodedBytes, Charset.defaultCharset())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getUsername(token: String): String {
        try {
            val substitute = token.toString().replace('-','+').replace('_','/')
            val split : List<String> =
                substitute.split(".")
            val total : String = getJson(split[1])
            val jsonTotal = JSONObject(total)
            val username : Any = jsonTotal.get("username")
            return username.toString()
        } catch (e: UnsupportedEncodingException) {
            throw Exception(e)
        }
    }
}