package com.example.marketplacepuj.ui.features.catalogo.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import by.alexandr7035.banking.ui.components.decoration.SkeletonShape
import by.alexandr7035.banking.ui.components.header.ScreenHeader
import com.example.marketplacepuj.R
import com.example.marketplacepuj.ui.features.cards.model.MoneyAmount
import com.example.marketplacepuj.ui.features.payment.AddPaymentCardButton
import com.example.marketplacepuj.ui.features.payment.HomeIntent
import com.example.marketplacepuj.ui.features.payment.HomeState
import com.example.marketplacepuj.ui.features.payment.PaymentCard
import com.example.marketplacepuj.ui.features.payment.PaymentViewModel
import com.example.marketplacepuj.ui.features.payment.ProfileUi
import com.example.marketplacepuj.ui.features.payment.SavingUi
import com.example.marketplacepuj.ui.theme.primaryFontFamily
import com.example.marketplacepuj.util.CardUi
import com.example.marketplacepuj.util.ErrorFullScreen
import com.example.marketplacepuj.util.MoneyAmountUi
import com.example.marketplacepuj.util.PrimaryButton
import com.example.marketplacepuj.util.PrimaryCard
import com.example.marketplacepuj.util.TextBtn
import org.koin.androidx.compose.koinViewModel


@Composable
fun PaymentScreen(
    viewModel: PaymentViewModel = koinViewModel(),
    navController: NavController,
    onCardDetails: (cardId: String) -> Unit = {},
    total: Double,
    onProceed: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.emitIntent(HomeIntent.EnterScreen)
    }

    val state = viewModel.state.collectAsStateWithLifecycle().value
    when (state) {
        is HomeState.Success -> PaymentScreen_Ui(
            state = state,
            onCardDetails = onCardDetails,
            navController = navController,
            total = total,
            onProceed = onProceed
        )

        is HomeState.Loading -> PaymentScreen_Skeleton()
        is HomeState.Error -> ErrorFullScreen(error = state.error, onRetry = {
            viewModel.emitIntent(HomeIntent.EnterScreen)
        })
    }
}

@Composable
fun OptionPaymentScreen(
    navController: NavController,
    total: Double,
    onPagoEfectivo: () -> Unit,
    onPagoTarjeta: () -> Unit
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.White,
                        Color(0xFFF2F2F2)
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },

                ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }

            Text(
                text = stringResource(id = R.string.metodo_pago),
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }

        PrimaryCard(
            modifier = Modifier.padding(horizontal = 24.dp),
            paddingValues = PaddingValues(vertical = 16.dp),
            corners = 16.dp
        ) {

            Column {
                Text(
                    text = stringResource(R.string.balance), style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF999999),
                    ), modifier = Modifier.padding(horizontal = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 8.dp),

                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = MoneyAmountUi.mapFromDomain(MoneyAmount(total.toFloat())).amountStr,
                        style = TextStyle(
                            fontSize = 32.sp,
                            fontFamily = primaryFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(16.dp))


        PrimaryButton(
            onClick = { onPagoEfectivo() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            text = stringResource(R.string.efectivo)

        )


        Spacer(modifier = Modifier.padding(16.dp))


        PrimaryButton(
            onClick = { onPagoTarjeta() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            text = stringResource(R.string.con_tarjeta)
        )

    }

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PaymentScreen_Ui(
    navController: NavController,
    total: Double,
    state: HomeState.Success,
    onCardDetails: (cardId: String) -> Unit = {},
    onProceed: () -> Unit
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.White,
                        Color(0xFFF2F2F2)
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },

                ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }

            Text(
                text = "Payments",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            if (state.cards.isNotEmpty()) {
                items(state.cards) { card ->
                    PaymentCard(cardUi = card, onCLick = { onCardDetails(card.id) })
                }

                if (state.cards.size < 2) {
                    item {
                        AddPaymentCardButton(onCLick = {
                            navController.navigate("add_card")
                        })
                    }
                }

            } else {
                item {
                    AddPaymentCardButton(onCLick = {
                        navController.navigate("add_card")
                    })
                }
            }
        }

        Spacer(modifier = Modifier.padding(16.dp))

        PrimaryCard(
            modifier = Modifier.padding(horizontal = 24.dp),
            paddingValues = PaddingValues(vertical = 16.dp),
            corners = 16.dp
        ) {

            Column {
                Text(
                    text = stringResource(R.string.balance), style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF999999),
                    ), modifier = Modifier.padding(horizontal = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 8.dp),

                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = MoneyAmountUi.mapFromDomain(MoneyAmount(total.toFloat())).amountStr,
                        style = TextStyle(
                            fontSize = 32.sp,
                            fontFamily = primaryFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(16.dp))

        PrimaryButton(
            onClick = { onProceed() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            text = stringResource(R.string.proceed),
            isEnabled = state.cards.isNotEmpty()
        )
        TextBtn(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .wrapContentSize()
                .padding(vertical = 8.dp),
            text = stringResource(id = R.string.cancel)
        )


    }

}


@Preview(showBackground = true)
@Composable
fun PaymentPreview() {
    PaymentScreen_Ui(
        rememberNavController(), 120.0,
        HomeState.Success(profile = ProfileUi.mock(), cards = List(3) {
            CardUi.mock()
        }, savings = List(3) {
            SavingUi.mock()
        }), {}, {}
    )

}


@Preview(showBackground = true)
@Composable
fun MetodoPagoPreview() {
    OptionPaymentScreen(
        rememberNavController(), 120.0,
        {}, {}
    )

}


@Composable
private fun PaymentScreen_Skeleton() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(bottom = 24.dp)
    ) {
        ScreenHeader(
            toolbar = {}, panelVerticalOffset = 24.dp
        ) {

        }

        Spacer(Modifier.height(8.dp))



        SkeletonShape(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .width(300.dp)
                .height(200.dp)
        )

        Spacer(Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            List(3) {
                SkeletonShape(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                )
            }
        }
    }
}


