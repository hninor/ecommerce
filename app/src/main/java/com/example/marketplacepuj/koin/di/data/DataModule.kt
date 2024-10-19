package by.alexandr7035.banking.core.di.data

import androidx.room.Room
import com.example.marketplacepuj.db.CacheDatabase
import com.example.marketplacepuj.db.convertors.MoneyAmountConvertor
import by.alexandr7035.banking.domain.features.cards.CardsRepository
import com.example.marketplacepuj.db.CardsRepositoryMock
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val dataModule = module {
    single {
        Room.databaseBuilder(
            androidApplication().applicationContext,
            CacheDatabase::class.java,
            "cache.db"
        )
            .fallbackToDestructiveMigration()

            .build()
    }

    single {
        val db: CacheDatabase = get()
        db.getCardsDao()
    }





    single<CardsRepository> {
        CardsRepositoryMock(
            cardsDao = get(),
            coroutineDispatcher = Dispatchers.IO
        )
    }


}