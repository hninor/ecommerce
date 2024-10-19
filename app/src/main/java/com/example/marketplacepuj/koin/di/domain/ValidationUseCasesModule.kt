package com.example.marketplacepuj.koin.di.domain

import by.alexandr7035.banking.domain.features.cards.GetHomeCardsUseCase
import com.example.marketplacepuj.domain.validation.ValidateBillingAddressUseCase
import com.example.marketplacepuj.domain.validation.ValidateCardExpirationUseCase
import com.example.marketplacepuj.domain.validation.ValidateCardHolderUseCase
import com.example.marketplacepuj.domain.validation.ValidateCardNumberUseCase
import com.example.marketplacepuj.domain.validation.ValidateCvvCodeUseCase
import com.example.marketplacepuj.domain.validation.ValidateEmailUseCase
import com.example.marketplacepuj.domain.validation.ValidatePasswordUseCase
import org.koin.dsl.module

val validationUseCasesModule = module {
    factory { ValidateCardNumberUseCase() }
    factory { ValidateCvvCodeUseCase() }
    factory { ValidateCardExpirationUseCase() }
    factory { ValidateBillingAddressUseCase() }
    factory { ValidateCardHolderUseCase() }
    factory { ValidatePasswordUseCase() }
    factory { ValidateEmailUseCase() }
    factory { GetHomeCardsUseCase(get()) }
}