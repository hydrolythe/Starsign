package com.example.starsign.cardformulars

import com.example.starsign.database.DatabaseCard

data class CardCreationResult(val success: DatabaseCard?=null, val exception: Exception?=null) {
}