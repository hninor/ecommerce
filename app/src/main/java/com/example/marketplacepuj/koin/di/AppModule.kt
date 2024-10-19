package by.alexandr7035.banking.core.di

import by.alexandr7035.banking.core.di.data.dataModule
import by.alexandr7035.banking.core.di.domain.cardUseCasesModule
import com.example.marketplacepuj.koin.di.domain.validationUseCasesModule
import com.example.marketplacepuj.koin.di.presentation.presentationModule
import org.koin.dsl.module

val appModule = module {


    includes(cardUseCasesModule)
    includes(validationUseCasesModule)


    includes(dataModule)
    includes(presentationModule)
}