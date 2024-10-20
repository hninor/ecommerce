package com.example.marketplacepuj.koin.di.presentation

import com.example.marketplacepuj.ui.features.catalogo.viewmodel.PedidoViewModel
import com.example.marketplacepuj.ui.features.payment.PaymentViewModel
import com.example.marketplacepuj.ui.features.payment.carddetail.CardDetailsViewModel
import com.example.marketplacepuj.ui.features.screen_add_card.AddCardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {


    viewModel {
        AddCardViewModel(
            validateCardNumberUseCase = get(),
            validateCvvCodeUseCase = get(),
            validateCardExpirationUseCase = get(),
            validateCardHolderUseCase = get(),
            validateBillingAddressUseCase = get(),
            addCardUseCase = get()
        )
    }

    viewModel {
        PaymentViewModel(
            getHomeCardsUseCase = get()
        )
    }


    viewModel {
        PedidoViewModel(

        )
    }


    viewModel {
        CardDetailsViewModel(
            getCardByIdUseCase = get(),
            deleteCardByNumberUseCase = get(),
            setCardAsPrimaryUseCase = get()
        )
    }

}