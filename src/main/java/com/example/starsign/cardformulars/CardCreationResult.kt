package com.example.starsign.cardformulars

import com.example.starsign.network.NetworkCard

data class CardCreationResult(val success: NetworkCard?=null, val exception: Exception?=null)