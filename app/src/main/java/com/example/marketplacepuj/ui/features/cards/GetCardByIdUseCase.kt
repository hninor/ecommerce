package by.alexandr7035.banking.domain.features.cards

import com.example.marketplacepuj.ui.features.cards.model.PaymentCard

class GetCardByIdUseCase(
    private val cardsRepository: CardsRepository
) {
    suspend fun execute(cardId: String): PaymentCard {
        return cardsRepository.getCardById(cardId)
    }
}