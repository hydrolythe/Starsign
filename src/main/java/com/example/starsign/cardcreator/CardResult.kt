package com.example.starsign.cardcreator

import com.example.starsign.database.DatabaseCard

data class CardResult(val success: List<DatabaseCard>?=null, val exception: Exception?=null) {
}