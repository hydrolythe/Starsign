package com.example.starsign.database

import androidx.room.Entity

@Entity(tableName = "Spell")
enum class Spell(){
    DAMAGE,
    HEAL,
    BOOSTHEALTH,
    BOOSTMP,
    BOOSTATTACK,
    BOOSTDEFENSE,
    BOOSTSPECIALATTACK,
    BOOSTSPECIALDEFENSE,
    LOWERATTACK,
    LOWERDEFENSE,
    LOWERSPECIALATTACK,
    LOWERSPECIALDEFENSE,
    SKIPTURN,
    DRAW,
    DISCARD,
    DESTROYFIELD,
    RECOVERFIELD,
    RECOVERHAND,
    RECOVERDECK,
    DESTROYDECK,
    RECOVERMP;
}