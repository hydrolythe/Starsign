package com.example.starsign.cardcreator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.starsign.cardformulars.CardCreationResult
import com.example.starsign.database.Card
import com.example.starsign.repository.ICardRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CardViewModel(val cardRepository:ICardRepository): ViewModel() {
    private var _cardResult = MutableLiveData<CardResult>()
    val cardResult : LiveData<CardResult>
        get() = _cardResult
    val cards = cardRepository.getCards()
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    fun deleteCards(cards:List<Card>){
        uiScope.launch{
            try{
                val result = cardRepository.removeCards(cards)
                _cardResult.value = CardResult(success=result)
            }
            catch(e:Exception){
                _cardResult.value = CardResult(exception=e)
            }
        }
    }
}