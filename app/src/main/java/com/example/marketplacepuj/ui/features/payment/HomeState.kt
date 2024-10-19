package com.example.marketplacepuj.ui.features.payment

import com.example.marketplacepuj.util.CardUi
import com.example.marketplacepuj.util.MoneyAmountUi
import com.example.marketplacepuj.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

sealed class HomeState {
    // Single loading for all parts of screen for simplicity
    object Loading : HomeState()

    data class Success(
        val profile: ProfileUi,
        val cards: List<CardUi> = emptyList(),
        val savings: List<SavingUi> = emptyList(),
        val balance: Flow<MoneyAmountUi?> = flowOf(null),
    ) : HomeState()

    data class Error(val error: UiText) : HomeState()
}
