package com.example.marketplacepuj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marketplacepuj.ui.features.catalogo.screens.CartScreen
import com.example.marketplacepuj.ui.features.catalogo.screens.CatalogueNavScreen
import com.example.marketplacepuj.ui.features.catalogo.screens.OrderScreen
import com.example.marketplacepuj.ui.features.catalogo.viewmodel.CatalogueViewModel
import com.example.marketplacepuj.ui.features.screen_add_card.AddCardScreen
import com.example.marketplacepuj.ui.theme.MarketplacePUJTheme
import com.example.marketplacepuj.util.LocalScopedSnackbarState
import com.example.marketplacepuj.util.ScopedSnackBarState


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MarketplacePUJTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }


    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {


}


@Composable
fun MyApp() {
    val catalogueViewModel: CatalogueViewModel = viewModel()
    val navController = rememberNavController()
    val snackBarHostState = remember { SnackbarHostState() }
    val hostCoroutineScope = rememberCoroutineScope()

    CompositionLocalProvider(
        LocalScopedSnackbarState provides ScopedSnackBarState(
            value = snackBarHostState,
            coroutineScope = hostCoroutineScope
        )
    ) {
        NavHost(navController, startDestination = BottomNavItem.Home.route) {
            composable(BottomNavItem.Home.route) {
                AddCardScreen(onBack = {
                    navController.popBackStack()
                })
                //CatalogueNavScreen(navController, catalogueViewModel)
            }
            composable(BottomNavItem.ShoppingCart.route) {
                CartScreen(
                    navController,
                    catalogueViewModel.cartItems,
                    { catalogueViewModel.onDeleteCartItem(it) }) {
                    catalogueViewModel.crearPedido()
                }
            }
            composable(BottomNavItem.Person.route) {
                //OrderScreen(navController)
            }
        }
    }


}

enum class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    Home("home", Icons.Default.Home, "Home"),
    ShoppingCart("shoppingCart", Icons.Default.ShoppingCart, "Cart"),
    Person("profile", Icons.Default.Person, "Profile")
}










