package com.example.marketplacepuj.util

import com.example.marketplacepuj.ui.features.cards.model.BalanceCurrency
import com.example.marketplacepuj.ui.features.cards.model.MoneyAmount
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

data class MoneyAmountUi(
    val amountStr: String,
) {
    companion object {
        fun mapFromDomain(balance: MoneyAmount): MoneyAmountUi {
            val symbols = DecimalFormatSymbols(Locale.US)
            val decimalFormat = DecimalFormat("#,##0.##", symbols)
            val formattedValue = decimalFormat.format(balance.value)


            // Add currency prefixes
            return when (balance.currency) {
                BalanceCurrency.USD -> {
                    MoneyAmountUi("$$formattedValue")
                }
            }
        }
    }
}
