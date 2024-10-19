package com.example.marketplacepuj.ui.features.payment.carddetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.features.cards.GetCardByIdUseCase
import by.alexandr7035.banking.domain.features.cards.RemoveCardUseCase
import by.alexandr7035.banking.domain.features.cards.SetCardAsPrimaryUseCase
import com.example.marketplacepuj.ui.features.cards.model.PaymentCard
import com.example.marketplacepuj.util.CardUi
import com.example.marketplacepuj.util.ErrorType
import com.example.marketplacepuj.util.OperationResult
import com.example.marketplacepuj.util.asUiTextError
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CardDetailsViewModel(
    private val getCardByIdUseCase: GetCardByIdUseCase,
    private val deleteCardByNumberUseCase: RemoveCardUseCase,
    private val setCardAsPrimaryUseCase: SetCardAsPrimaryUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<CardDetailsState> = MutableStateFlow(CardDetailsState())
    val state = _state.asStateFlow()

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        reduceError(ErrorType.fromThrowable(throwable))
    }

    fun emitIntent(intent: CardDetailsIntent) {
        when (intent) {
            is CardDetailsIntent.EnterScreen -> {
                reduceScreenLoading()

                viewModelScope.launch(errorHandler) {
                    val card = getCardByIdUseCase.execute(intent.cardId)


                    reduceSuccessData(card)
                }
            }

            is CardDetailsIntent.ToggleDeleteCardDialog -> {
                reduceDeleteCardDialog(
                    isShown = intent.isDialogShown
                )
            }

            is CardDetailsIntent.ConfirmDeleteCard -> {
                reduceDeleteCard()
            }

            is CardDetailsIntent.SetCardAsPrimary -> {
                viewModelScope.launch(errorHandler) {
                    val res = OperationResult.runWrapped {
                        setCardAsPrimaryUseCase.execute(
                            cardId = intent.cardId,
                            setAsPrimary = intent.makePrimary
                        )
                    }

                    when (res) {
                        is OperationResult.Success -> {
                            _state.update {
                                it.copy(
                                    setCardAsPrimaryEvent = triggered(res),
                                    card = it.card?.copy(isPrimary = intent.makePrimary)
                                )
                            }
                        }

                        is OperationResult.Failure -> {
                            _state.update {
                                it.copy(
                                    setCardAsPrimaryEvent = triggered(res),
                                )
                            }
                        }
                    }
                }
            }
        }
    }


    private fun reduceScreenLoading() {
        _state.update {
            it.copy(
                showCardSkeleton = true
            )
        }
    }

    private fun reduceSuccessData(
        card: PaymentCard
    ) {
        _state.update {
            it.copy(
                showCardSkeleton = false,
                card = CardUi.mapFromDomain(
                    card = card,
                    balanceFlow = flowOf("0"),
                )
            )
        }
    }

    private fun reduceDeleteCardDialog(
        isShown: Boolean
    ) {
        _state.update {
            it.copy(
                showDeleteCardDialog = isShown
            )
        }
    }

    private fun reduceDeleteCard() {
        viewModelScope.launch(errorHandler) {
            _state.update {
                it.copy(showLoading = true)
            }

            _state.value.card?.id?.let { cardId ->
                val res = OperationResult.runWrapped {
                    deleteCardByNumberUseCase.execute(cardId)
                }

                when (res) {
                    is OperationResult.Success -> {
                        _state.update {
                            it.copy(
                                card = it.card?.copy(
                                    balanceFlow = emptyFlow()
                                ),
                                cardDeletedResultEvent = triggered(res)
                            )
                        }
                    }

                    is OperationResult.Failure -> {
                        _state.update {
                            it.copy(
                                showLoading = false,
                                cardDeletedResultEvent = triggered(res)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun reduceError(error: ErrorType) {
        _state.update {
            it.copy(
                showCardSkeleton = false,
                error = error.asUiTextError()
            )
        }
    }

    fun consumeDeleteResultEvent() {
        _state.update {
            it.copy(
                showLoading = false,
                cardDeletedResultEvent = consumed()
            )
        }
    }

    fun consumeSetCardAsDefaultEvent() {
        _state.update {
            it.copy(
                showLoading = false,
                setCardAsPrimaryEvent = consumed()
            )
        }
    }
}