package com.example.marketplacepuj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marketplacepuj.ui.features.catalogo.screens.CartScreenHost
import com.example.marketplacepuj.ui.features.catalogo.screens.CatalogueNavScreen
import com.example.marketplacepuj.ui.features.catalogo.screens.OrderScreenHost
import com.example.marketplacepuj.ui.features.catalogo.viewmodel.CatalogueViewModel
import com.example.marketplacepuj.ui.theme.MarketplacePUJTheme


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

    NavHost(navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) {
            CatalogueNavScreen(navController, catalogueViewModel)
        }
        composable(BottomNavItem.ShoppingCart.route) {
            CartScreenHost(
                navController,
                catalogueViewModel.cartItems,
                catalogueViewModel,
                {
                    catalogueViewModel.onDeleteCartItem(it)
                }
            ) {
                catalogueViewModel.crearPedido()
                navController.navigate(BottomNavItem.Home.route)
            }
        }
        composable(BottomNavItem.Person.route) {
            OrderScreenHost(navController = navController)
        }
    }


}

enum class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    Home("home", Icons.Default.Home, "Home"),
    ShoppingCart("shoppingCart", Icons.Default.ShoppingCart, "Cart"),
    Person("profile", Icons.Default.Person, "Profile")
}










