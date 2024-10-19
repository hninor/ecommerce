package by.alexandr7035.banking.domain.features.cards

import com.example.marketplacepuj.ui.features.cards.model.AddCardPayload
import com.example.marketplacepuj.ui.features.cards.model.PaymentCard

interface CardsRepository {
    suspend fun getCards(): List<PaymentCard>
    suspend fun addCard(data: AddCardPayload)
    suspend fun getCardById(id: String): PaymentCard
    suspend fun deleteCardById(id: String)
    suspend fun markCardAsPrimary(cardId: String, isPrimary: Boolean = false)
}