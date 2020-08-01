package mockRepository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.starsign.database.*
import com.example.starsign.repository.ICardRepository
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.NullPointerException

class FakeCardRepository(private val listdbCard: MutableList<DatabaseCard>) : ICardRepository {
    private val cards = MutableLiveData<MutableList<DatabaseCard>>()
    val lcards: LiveData<List<Card>> = Transformations.map(cards){
        it.asDomainModel()
    }

    override suspend fun addCard(card: Card): DatabaseCard {
        val dbCard = fromCardToDatabaseCard(card)
        if (cards.value != null) {
            if (cards.value?.asDomainModel()?.contains(card)!!) {
                throw IllegalArgumentException()
            }
            else{
                cards.value!!.add(dbCard)
            }
        }
        cards.value = mutableListOf(dbCard)
        return dbCard
    }

    override suspend fun removeCards(cardstoRemove: List<Card>): List<DatabaseCard> {
        cardstoRemove.forEach{
            if(!cards.value?.asDomainModel()?.contains(it)!!){
                throw NullPointerException()
            }
        }
        val dbCards = mutableListOf<DatabaseCard>()
        val listcards = cards.value?.asDomainModel()?.toMutableList()
        cardstoRemove.forEach{
            dbCards.add(cards.value?.get(listcards?.indexOf(it)!!)!!)
            cards.value?.removeAt(listcards?.indexOf(it)!!)
        }
        return dbCards
    }

    override suspend fun editCard(card: DatabaseCard): Response<Any> {
        if(!cards.value!!.map{it.cardid}.contains(card.cardid)){
            return Response.error(400, ResponseBody.create(MediaType.get("application/json"), "Bad request"))
        }
        cards.value!!.set(cards.value!!.map{it.cardid}.get(card.cardid.toInt()).toInt(), card)
        return Response.success(204, ResponseBody.create(MediaType.get("application/json"), "No content"))
    }

    override suspend fun refreshCards() {
        if(cards.value==null) {
            cards.value = listdbCard
        }
    }

    override fun getCardOnDetail(title: String): DatabaseCard? {
        val index = cards.value!!.map{it.title}.indexOf(title)
        if(index>=0) {
            return cards.value!!.get(index)
        }
        else{
            return null
        }
    }

    override fun getDomainCards(): LiveData<List<Card>> {
        return lcards
    }

    private fun fromCardToDatabaseCard(card:Card):DatabaseCard{
        return when (card) {
            is Monster -> DatabaseMonster(
                cardid = cards.value?.size?.toLong() ?: 0,
                title = card.title,
                manarequirements = card.manarequirements,
                life = card.life,
                attack = card.attack,
                defense = card.defense,
                magicattack = card.magicattack,
                magicdefense = card.magicdefense,
                mp = card.mp,
                spells = card.spells
            )
            is Magic -> DatabaseMagic(
                cardid = cards.value?.size?.toLong() ?: 0,
                title = card.title,
                manaamount = card.manaamount,
                species = card.species ?: throw NullPointerException(),
                spells = card.spells
            )
            is Source ->
                DatabaseSource(cardid = cards.value?.size?.toLong() ?: 0, title = card.title, source = card.source)
            else -> throw IllegalArgumentException("The type can not be converted")
        }
    }

}