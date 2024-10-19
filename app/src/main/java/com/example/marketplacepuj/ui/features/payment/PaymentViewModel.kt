package com.example.marketplacepuj.ui.features.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.features.cards.GetHomeCardsUseCase
import com.example.marketplacepuj.util.CardUi
import com.example.marketplacepuj.util.ErrorType
import com.example.marketplacepuj.util.asUiTextError
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val getHomeCardsUseCase: GetHomeCardsUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(
        HomeState.Loading
    )

    val state = _state.asStateFlow()

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        reduceError(ErrorType.fromThrowable(exception))
    }

    fun emitIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.EnterScreen -> loadData()
        }
    }

    private fun loadData() {
        _state.update {
            HomeState.Loading
        }

        viewModelScope.launch(errorHandler) {


            val cardsJob = async() {
                val res = getHomeCardsUseCase.execute()

                res.map {
                    CardUi.mapFromDomain(
                        card = it
                    )
                }
            }


            val cards = cardsJob.await()


            // Success state
            reduceData(
                profile = ProfileUi.mock(),
                cards = cards,
                savings = emptyList()
            )
        }
    }

    private fun reduceData(
        profile: ProfileUi,
        cards: List<CardUi>,
        savings: List<SavingUi>
    ) {
        _state.update {
            HomeState.Success(
                profile = profile,
                cards = cards,
                savings = savings
            )
        }
    }

    private fun reduceError(error: ErrorType) {
        _state.update {
            HomeState.Error(
                error = error.asUiTextError()
            )
        }
    }


}