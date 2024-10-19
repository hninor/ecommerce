package com.example.marketplacepuj.ui.features.payment.carddetail

import com.example.marketplacepuj.util.CardUi
import com.example.marketplacepuj.util.OperationResult
import com.example.marketplacepuj.util.UiText
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class CardDetailsState(
    val card: CardUi? = null,
    val showCardSkeleton: Boolean = true,
    val error: UiText? = null,
    val showLoading: Boolean = false,
    val showDeleteCardDialog: Boolean = false,
    val cardDeletedResultEvent: StateEventWithContent<OperationResult<Unit>> = consumed(),
    val setCardAsPrimaryEvent: StateEventWithContent<OperationResult<Unit>> = consumed()
)
