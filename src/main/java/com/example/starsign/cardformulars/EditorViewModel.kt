package com.example.starsign.cardformulars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.starsign.database.Card
import com.example.starsign.database.DatabaseCard
import com.example.starsign.repository.CardRepository
import com.example.starsign.repository.ICardRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class EditorViewModel(val cardRepository: ICardRepository): ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var _newCard = MutableLiveData<Card>()
    val newCard : LiveData<Card>
    get() = _newCard
    private var _cardEditResult = MutableLiveData<CardEditResult>()
    val cardEditResult : LiveData<CardEditResult>
    get() = _cardEditResult

    inline fun <reified T:DatabaseCard?> getDbCard(title: String):T?{
        val dbCard = cardRepository.getCardOnDetail(title)
        if(dbCard is T){
            return dbCard
        } else{
            return null
        }
    }

    fun updateCard(card: DatabaseCard){
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