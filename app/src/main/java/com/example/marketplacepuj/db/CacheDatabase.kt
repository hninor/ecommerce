package com.example.marketplacepuj.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import by.alexandr7035.banking.data.cards.cache.CardEntity
import by.alexandr7035.banking.data.cards.cache.CardsDao
import com.example.marketplacepuj.db.convertors.MoneyAmountConvertor

@Database(entities = [CardEntity::class], version = 1)
@TypeConverters(MoneyAmountConvertor::class)
abstract class CacheDatabase : RoomDatabase() {
    abstract fun getCardsDao(): CardsDao

}
