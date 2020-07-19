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

    override suspend fun addCard(card: Card): DatabaseCard {
        val dbCard = fromCardToDatabaseCard(card)
        cards.value!!.add(dbCard)
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
            return Response.error(400, ResponseBody.create(MediaType.get("Bad Request"), "Bad request"))
        }
        cards.value!!.set(cards.value!!.map{it.cardid}.get(card.cardid), card)
        return Response.success(204, ResponseBody.create(MediaType.get("No Content"), "No content"))
    }

    override suspend fun refreshCards() {
        if(cards.value==null) {
            cards.value = listdbCard
        }
    }

    override fun getCardOnDetail(title: String): DatabaseCard? {
        return cards.value!!.get(cards.value!!.map{it.title}.indexOf(title))
    }

    override fun getDomainCards(): List<Card> {
        val result = cards.value?.asDomainModel()!!
        return cards.value?.asDomainModel()!!
    }

    private fun fromCardToDatabaseCard(card:Card):DatabaseCard{
        return when (card) {
            is Monster -> DatabaseMonster(
                cardid = cards.value!!.size,
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
                cardid = cards.value!!.size,
                title = card.title,
                manaamount = card.manaamount,
                species = card.species,
                spells = card.spells
            )
            is Source ->
                DatabaseSource(cardid = cards.value!!.size, title = card.title, source = card.source)
            else -> throw IllegalArgumentException("The type can not be converted")
        }
    }

}