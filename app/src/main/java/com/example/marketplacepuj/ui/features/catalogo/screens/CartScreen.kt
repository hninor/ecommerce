package com.example.marketplacepuj.ui.features.catalogo.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil3.compose.AsyncImage
import com.example.marketplacepuj.BottomNavItem
import com.example.marketplacepuj.ui.features.catalogo.viewmodel.CatalogueViewModel
import com.example.marketplacepuj.ui.features.payment.carddetail.CardDetailsScreen
import com.example.marketplacepuj.ui.features.screen_add_card.AddCardScreen
import com.example.marketplacepuj.util.LocalScopedSnackbarState
import com.example.marketplacepuj.util.ScopedSnackBarState
import java.text.NumberFormat


val items = listOf(
    CartItem("Lorem ipsum dolor sit", 1000.0, "", "1"),
    CartItem("Lorem ipsum dolor sit", 1000.0, "", "1"),
    CartItem("Lorem ipsum dolor sit", 1000.0, "", "1"),
    CartItem("Lorem ipsum dolor sit", 1000.0, "", "1"),
    CartItem("Lorem ipsum dolor sit", 1000.0, "", "1"),
    CartItem("Lorem ipsum dolor sit", 1000.0, "", "1"),
    CartItem("Lorem ipsum dolor sit", 1000.0, "", "1"),
    CartItem("Lorem ipsum dolor sit", 1000.0, "", "1"),
)


@Composable
fun CartScreenHost(
    navController: NavController,
    cartItems: List<CartItem>,
    catalogueViewModel: CatalogueViewModel,
    onDeleteCartItem: (cartItem: CartItem) -> Unit,
    onProceed: () -> Unit

) {
    val navControllerCart = rememberNavController()
    val snackBarHostState = remember { SnackbarHostState() }
    val hostCoroutineScope = rememberCoroutineScope()

    CompositionLocalProvider(
        LocalScopedSnackbarState provides ScopedSnackBarState(
            value = snackBarHostState,
            coroutineScope = hostCoroutineScope
        )
    ) {
        NavHost(navControllerCart, startDestination = "cart") {
            composable("cart") {
                CartScreen(
                    navController,
                    cartItems,
                    { onDeleteCartItem(it) }) {
                    navControllerCart
                        .navigate("metodo_pago")
                }
            }

            composable("metodo_pago") {

                OptionPaymentScreen(
                    navController = navController,
                    total = cartItems.sumOf { it.price },
                    onPagoEfectivo = {
                        catalogueViewModel.showDialog = true
                    },
                    onPagoTarjeta = {
                        navControllerCart.navigate("payment")
                    },
                    showDialog = catalogueViewModel.showDialog,
                    onDissmissDialog = {
                        catalogueViewModel.showDialog = false
                        onProceed()
                    }
                )
            }


            composable("payment") {
                PaymentScreen(
                    navController = navControllerCart,
                    total = cartItems.sumOf { it.price },
                    onProceed = {
                        catalogueViewModel.showDialog = true
                    },
                    onCardDetails = {
                        navControllerCart.navigate("cardDetails/$it")
                    },
                    showDialog = catalogueViewModel.showDialog,
                    onDissmissDialog = {
                        catalogueViewModel.showDialog = false
                        onProceed()
                    }
                )
            }

            composable("add_card") {
                AddCardScreen(onBack = {
                    navControllerCart.popBackStack()
                })
            }

            composable(
                route = "cardDetails/{cardId}",
                arguments = listOf(navArgument("cardId") { type = NavType.StringType })
            ) {
                val cardId = it.arguments?.getString("cardId")!!

                CardDetailsScreen(
                    cardId = cardId,
                    onBack = {
                        navControllerCart.popBackStack()
                    }
                )
            }
        }
    }

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CartScreen(
    navController: NavController,
    cartItems: List<CartItem>,
    onDeleteCartItem: (cartItem: CartItem) -> Unit,
    onCheckout: () -> Unit
) {


    val customShape =
        RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp, bottomEnd = 32.dp, bottomStart = 32.dp)

    val total = cartItems.sumOf { it.price }

    val formatter = NumberFormat.getCurrencyInstance().apply {
        maximumFractionDigits = 0
    }
    val formattedPrice = formatter.format(total)
    Scaffold(
        bottomBar = {

            BottomNavigationBar(
                navController = navController, modifier = Modifier
                    .padding(16.dp)
                    .clip(customShape)
            )


        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF243484), Color(0xFF2be4dc))
                    )
                )
                .padding(bottom = 72.dp),
            verticalArrangement = Arrangement.SpaceBetween

        ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        if (!navController.popBackStack()) {
                            navController.navigate(BottomNavItem.Home.route)
                        }

                    },

                    ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Text(
                    text = "Cart",
                    style = MaterialTheme.typography.h5,

                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }


            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(2f)
            ) {
                items(cartItems) { item ->
                    CartItemRow(item = item, onDeleteCartItem)
                }


            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {
                    Text(
                        text = "Total:",
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold,
                        color = Color.LightGray
                    )
                    Text(
                        text = formattedPrice,
                        style = MaterialTheme.typography.h6,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Botón Checkout
                Button(
                    onClick = { onCheckout() },
                    modifier = Modifier
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFFF7F50), // Naranja claro
                        contentColor = Color.White
                    ),
                    enabled = total != 0.0
                ) {
                    Text(text = "Checkout")
                }

            }


        }
    }
}

@Composable
fun CartItemRow(item: CartItem, onDeleteCartItem: (cartItem: CartItem) -> Unit) {

    val formatter = NumberFormat.getCurrencyInstance().apply {
        maximumFractionDigits = 0
    }
    val formattedPrice = formatter.format(item.price)

    Card(
        shape = RoundedCornerShape(16.dp), // Adjust the corner radius as needed
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()

    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = item.imageUrl, // Reemplaza con tu imagen
                contentDescription = "Imagen centrada",
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .size(60.dp),
            )
            Column(
                Modifier
                    .weight(2f)
                    .padding(start = 8.dp)
            ) {
                Text(text = item.name, fontWeight = FontWeight.Bold)
                Text(text = formattedPrice, color = Color.Gray, fontWeight = FontWeight.Bold)
            }
            IconButton(onClick = { onDeleteCartItem(item) }) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Remove",
                    tint = Color.Gray
                )


            }
        }

    }
}

data class CartItem(
    val name: String,
    val price: Double,
    val imageUrl: String,
    val idProducto: String
)

@Preview(showBackground = true)
@Composable
fun CartPreview() {
    CartScreen(rememberNavController(), items, {}, {})

}


