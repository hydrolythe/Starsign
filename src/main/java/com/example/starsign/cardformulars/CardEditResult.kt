package com.example.starsign.cardformulars

import com.example.starsign.database.DatabaseCard
import retrofit2.Response

class CardEditResult(val success: Response<Any>?=null, val exception: Exception?=null)