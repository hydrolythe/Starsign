package com.example.starsign.cardcreator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.starsign.repository.ICardRepository
import java.lang.IllegalArgumentException

class CardCreatorViewModelFactory(private val cardRepository: ICardRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CardCreatorViewModel::class.java)){
            return CardCreatorViewModel(cardRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModelClass")
    }

}