package com.example.starsign.cardformulars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.starsign.data.Resulting
import com.example.starsign.database.Card
import com.example.starsign.repository.ICardRepository
import kotlinx.coroutines.*

class CardCreatorViewModel(val cardRepository: ICardRepository) : ViewModel() {
    private var _cardCreationResult = MutableLiveData<CardCreationResult>()
    val cardCreationResult : LiveData<CardCreationResult>
    get() = _cardCreationResult
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    fun insertCard(card: Card){
        uiScope.launch{
            try {
                val result = cardRepository.addCard(card)
                _cardCreationResult.value = CardCreationResult(success=result)
            }
            catch(e:Exception){
                _cardCreationResult.value = CardCreationResult(exception=e)
            }
        }
    }
}
