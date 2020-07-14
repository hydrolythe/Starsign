package com.example.starsign.cardformulars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.starsign.database.Card
import com.example.starsign.database.DatabaseCard
import com.example.starsign.observers.Observer
import com.example.starsign.observers.Subject
import com.example.starsign.repository.ICardRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EditorViewModel(val cardRepository: ICardRepository): ViewModel(), Subject {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _cardEditResult = MutableLiveData<CardEditResult>()
    val cardEditResult : MutableLiveData<CardEditResult>
    get() = _cardEditResult

    private val edit = mutableListOf<Observer>()

    fun getDbCard(card:Card):DatabaseCard?{
        return cardRepository.getCardOnDetail(card.title)
    }

    fun updateCard(card: DatabaseCard){
        uiScope.launch{
            try{
                val result = cardRepository.editCard(card)
                 notifyObservers(card.asDomainModel())
                _cardEditResult.value = CardEditResult(success=result)
            }
            catch(e:Exception){
                _cardEditResult.value = CardEditResult(exception=e)
            }
        }
    }

    override fun registerObserver(o: Observer) {
        edit.add(o)
    }

    override fun notifyObservers(card: Card) {
        edit.forEach { o -> o.update(card) }
    }

    override fun removeObserver(o: Observer) {
        edit.remove(o)
    }
}