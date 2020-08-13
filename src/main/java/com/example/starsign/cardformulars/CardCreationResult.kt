package com.example.starsign.cardformulars

import com.example.starsign.database.DatabaseCard
import com.example.starsign.database.NetworkCard

data class CardCreationResult(val success: NetworkCard?=null, val exception: Exception?=null)