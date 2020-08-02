package com.example.starsign.cardformulars

import com.example.starsign.database.DatabaseCard
import retrofit2.Response

class DatabaseCardResult(val success: DatabaseCard?=null, val exception: Exception?=null) {
}