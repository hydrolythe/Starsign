package com.example.starsign.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromSpellspecies(value:Int?):SpellSpecies?{
        if(value == 0){
            return SpellSpecies.FIELD
        } else if(value == 1){
            return SpellSpecies.EQUIPMENT
        } else {
            return SpellSpecies.EVENT
        }
    }

    @TypeConverter
    fun spellspeciesToInt(species: SpellSpecies?):Int?{
        if(species == SpellSpecies.FIELD){
            return 0
        }
        else if(species == SpellSpecies.EQUIPMENT){
            return 1
        } else {
            return 2
        }
    }

    @TypeConverter
    fun fromSpells(value:Int?):Map<Spell,Int>?{
        val mspint = mutableMapOf<Spell,Int>()
        val primes = listOf(2,3,5,7,11,13,17,19,23)
        val spells = listOf(Spell.DAMAGE, Spell.BOOSTATTACK, Spell.DRAW, Spell.BOOSTSPECIALATTACK, Spell.BOOSTDEFENSE, Spell.BOOSTHEALTH, Spell.BOOSTMP, Spell.BOOSTSPECIALDEFENSE, Spell.SKIPTURN)
        primes.forEach {
            if(value?.rem(it)?.equals(0)!!){
                var substitute = value
                var indexnr = 1
                substitute /= it
                while(substitute.rem(it) == 0){
                    substitute /= it
                    indexnr++
                }
                mspint[spells[primes.indexOf(it)]] = indexnr
            }
        }
        return mspint
    }

    @TypeConverter
    fun spelltoLong(mspint: Map<Spell,Int>?):Int?{
        val primes = listOf(2,3,5,7,11,13,17,19,23)
        val spells = listOf(Spell.DAMAGE, Spell.BOOSTATTACK, Spell.DRAW, Spell.BOOSTSPECIALATTACK, Spell.BOOSTDEFENSE, Spell.BOOSTHEALTH, Spell.BOOSTMP, Spell.BOOSTSPECIALDEFENSE, Spell.SKIPTURN)
        var savedLong = 1
        mspint?.forEach {
            var prime = primes[spells.indexOf(it.key)]
            for(i in 1 until it.value){
                prime *=prime
            }
            savedLong*=prime
        }
        return savedLong
    }

    @TypeConverter
    fun fromManas(value:Int?):Map<Mana,Int>?{
        val mmint = mutableMapOf<Mana,Int>()
        val primes = listOf(2,3,5,7,11,13)
        val manas = listOf(Mana.VOID, Mana.APEIRON, Mana.TIME, Mana.ATOM, Mana.FORM, Mana.SOUL)
        primes.forEach {
            if(value?.rem(it)?.equals(0)!!){
                var substitute = value
                var indexnr = 1
                substitute /= it
                while(substitute.rem(it) == 0){
                    substitute /= it
                    indexnr++
                }
                mmint[manas[primes.indexOf(it)]] = indexnr
            }
        }
        return mmint
    }

    @TypeConverter
    fun manatoLong(mmint: Map<Mana,Int>?):Int?{
        val primes = listOf(2,3,5,7,11,13)
        val manas = listOf(Mana.VOID, Mana.APEIRON, Mana.TIME, Mana.ATOM, Mana.FORM, Mana.SOUL)
        var savedLong = 1
        mmint?.forEach {
            var prime = primes[manas.indexOf(it.key)]
            for(i in 1 until it.value){
                prime *=prime
            }
            savedLong*=prime
        }
        return savedLong
    }
}