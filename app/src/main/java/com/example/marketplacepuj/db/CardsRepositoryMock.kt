package com.example.marketplacepuj.db

import by.alexandr7035.banking.data.cards.cache.CardEntity
import by.alexandr7035.banking.data.cards.cache.CardsDao
import by.alexandr7035.banking.domain.features.cards.CardsRepository
import com.example.marketplacepuj.ui.features.cards.model.AddCardPayload
import com.example.marketplacepuj.ui.features.cards.model.CardType
import com.example.marketplacepuj.ui.features.cards.model.MoneyAmount
import com.example.marketplacepuj.ui.features.cards.model.PaymentCard
import com.example.marketplacepuj.util.AppError
import com.example.marketplacepuj.util.ErrorType
import com.example.marketplacepuj.util.MockCardConstants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class CardsRepositoryMock(
    private val cardsDao: CardsDao,
    private val coroutineDispatcher: CoroutineDispatcher
) : CardsRepository {
    override suspend fun getCards(): List<PaymentCard> = withContext(coroutineDispatcher) {
        delay(MOCK_DELAY)

        return@withContext cardsDao.getCards().map { cardEntity ->
            mapCachedCardToDomain(cardEntity)
        }
    }

    override suspend fun addCard(data: AddCardPayload) = withContext(coroutineDispatcher) {
        val card = cardsDao.getCardByNumber(data.cardNumber)

        if (card == null) {
            delay(MOCK_DELAY)

            val entity = mapAddCardPayloadToCache(data)
            cardsDao.addCard(entity)
        } else {
            throw AppError(ErrorType.CARD_ALREADY_ADDED)
        }
    }

    override suspend fun getCardById(id: String): PaymentCard = withContext(coroutineDispatcher) {
        val cardEntity = cardsDao.getCardByNumber(id) ?: throw AppError(ErrorType.CARD_NOT_FOUND)
        delay(MOCK_DELAY)
        return@withContext mapCachedCardToDomain(cardEntity)
    }

    private fun mapCachedCardToDomain(cardEntity: CardEntity) = PaymentCard(
        cardId = cardEntity.number,
        cardNumber = cardEntity.number,
        isPrimary = cardEntity.isPrimary,
        cardHolder = cardEntity.cardHolder,
        addressFirstLine = cardEntity.addressFirstLine,
        addressSecondLine = cardEntity.addressSecondLine,
        expiration = cardEntity.expiration,
        addedDate = cardEntity.addedDate,
        recentBalance = MoneyAmount(cardEntity.recentBalance),
        cardType = cardEntity.cardType
    )

    private fun mapAddCardPayloadToCache(addCardPayload: AddCardPayload): CardEntity {
        val type = MockCardConstants.cardTypeByNumber(addCardPayload.cardNumber) ?: CardType.DEBIT

        return CardEntity(
            number = addCardPayload.cardNumber,
            isPrimary = false,
            cardHolder = addCardPayload.cardHolder,
            addressFirstLine = addCardPayload.addressFirstLine,
            addressSecondLine = addCardPayload.addressSecondLine,
            expiration = addCardPayload.expirationDate,
            addedDate = System.currentTimeMillis(),
            recentBalance = MOCK_CARD_INITIAL_BALANCE,
            cardType = type
        )
    }

    override suspend fun deleteCardById(id: String) {
        val cardEntity = cardsDao.getCardByNumber(id) ?: throw AppError(ErrorType.CARD_NOT_FOUND)
        delay(MOCK_DELAY)
        cardsDao.deleteCard(cardEntity)
    }

    override suspend fun markCardAsPrimary(cardId: String, isPrimary: Boolean) {
        when (isPrimary) {
            true -> cardsDao.markCardAsPrimary(cardId)
            false -> cardsDao.unmarkCardAsPrimary(cardId)
        }
    }

    companion object {
        private const val MOCK_DELAY = 500L
        private const val MOCK_CARD_INITIAL_BALANCE = 0f
    }
}