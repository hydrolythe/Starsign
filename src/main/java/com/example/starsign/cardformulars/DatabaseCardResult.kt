package com.example.starsign.cardformulars

import com.example.starsign.database.DatabaseCard
import com.example.starsign.database.NetworkCard
import retrofit2.Response

class DatabaseCardResult(val success: NetworkCard?=null, val exception: Exception?=null)