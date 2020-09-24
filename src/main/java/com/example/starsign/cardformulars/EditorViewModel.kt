package com.example.starsign.cardformulars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.starsign.database.Card
import com.example.starsign.network.NetworkCard
import com.example.starsign.repository.ICardRepository
import kotlinx.coroutines.*

class EditorViewModel(val cardRepository: ICardRepository): ViewModel() {
    private val viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var _newCard = MutableLiveData<Card>()
    val newCard : LiveData<Card>
    get() = _newCard
    private var _cardEditResult = MutableLiveData<CardEditResult>()
    val cardEditResult : LiveData<CardEditResult>
    get() = _cardEditResult
    private var _dbCardResult = MutableLiveData<DatabaseCardResult>()
    val dbCardResult : LiveData<DatabaseCardResult>
    get() = _dbCardResult

    init{
        runBlocking {
            cardRepository.refreshCards()
        }
    }

    fun <T: NetworkCard?> getDbCard(title: String){
        uiScope.launch {
            try {
                val dbCard = cardRepository.getCardOnDetail(title)
                _dbCardResult.value = DatabaseCardResult(success=dbCard)
            } catch(e:Exception){
                _dbCardResult.value = DatabaseCardResult(exception=e)
            }
        }
    }

    fun updateCard(card: NetworkCard){
        uiScope.launch{
            try{
                val result = cardRepository.editCard(card)
                if(result.isSuccessful) {
                    _newCard.value = card.asDomainModel()
                    _cardEditResult.value = CardEditResult(success = result)
                }
                else {
                    throw Exception()
                }
            }
            catch(e:Exception){
                _cardEditResult.value = CardEditResult(exception=e)
            }
        }
    }
}