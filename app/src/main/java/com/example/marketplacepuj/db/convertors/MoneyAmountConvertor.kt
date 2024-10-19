package com.example.marketplacepuj.db.convertors

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.marketplacepuj.ui.features.cards.model.MoneyAmount

@ProvidedTypeConverter
class MoneyAmountConvertor {
    @TypeConverter
    fun moneyAmountToDb(value: MoneyAmount?): Float? {
        return value?.value
    }

    @TypeConverter
    // FIXME currency when needed
    fun moneyAmountFromDb(value: Float?): MoneyAmount? {
        return if (value != null) {
            MoneyAmount(value)
        } else {
            null
        }
    }
}