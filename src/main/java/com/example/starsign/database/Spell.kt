package com.example.starsign.database

import androidx.room.Entity

@Entity(tableName = "Spell")
enum class Spell {
    DAMAGE,
    BOOSTHEALTH,
    BOOSTMP,
    BOOSTATTACK,
    BOOSTDEFENSE,
    BOOSTSPECIALATTACK,
    BOOSTSPECIALDEFENSE,
    SKIPTURN,
    DRAW
}