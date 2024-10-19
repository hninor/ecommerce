package by.alexandr7035.banking.domain.features.cards

import com.example.marketplacepuj.ui.features.cards.model.AddCardPayload

class AddCardUseCase(
    private val cardsRepository: CardsRepository
) {
    suspend fun execute(payload: AddCardPayload) {
        cardsRepository.addCard(payload)
    }
}