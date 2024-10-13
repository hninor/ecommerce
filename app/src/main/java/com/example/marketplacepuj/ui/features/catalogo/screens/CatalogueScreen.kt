package com.example.marketplacepuj.ui.features.catalogo.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.marketplacepuj.BottomNavItem
import com.example.marketplacepuj.R


data class Category(
    val id: Int, val name: String, val subcategories: List<Subcategory>
)

data class Subcategory(
    val id: Int, val name: String, val products: List<Product>
)

// Datos de muestra (dummy data)
data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val subCategory: String,
    val imageUrl: String,
    val price: Double
)

val products = listOf(
    Product(1, "iPhone 14", "Telefonía", "Smartphones", "https://via.placeholder.com/150", 999.99),
    Product(1, "iPhone 14", "Telefonía", "Smartphones", "https://via.placeholder.com/150", 999.99),
    Product(1, "iPhone 14", "Telefonía", "Smartphones", "https://via.placeholder.com/150", 999.99),
    Product(1, "iPhone 14", "Telefonía", "Smartphones", "https://via.placeholder.com/150", 999.99),
    Product(1, "iPhone 14", "Telefonía", "Smartphones", "https://via.placeholder.com/150", 999.99),
    Product(1, "iPhone 14", "Telefonía", "Smartphones", "https://via.placeholder.com/150", 999.99),
    Product(1, "iPhone 14", "Telefonía", "Smartphones", "https://via.placeholder.com/150", 999.99),
    Product(1, "iPhone 14", "Telefonía", "Smartphones", "https://via.placeholder.com/150", 999.99),
    Product(1, "iPhone 14", "Telefonía", "Smartphones", "https://via.placeholder.com/150", 999.99),
    // ... más productos
)

// Conjunto de datos de prueba
val categories = listOf(
    Category(
        1, "Deportes", listOf(
            Subcategory(1, "Ciclismo", products),
            Subcategory(2, "Natación", products),
            Subcategory(3, "Gimnasia", products)
        )
    ), Category(
        2, "Lucha", listOf(
            Subcategory(1, "Ciclismo", products),
            Subcategory(2, "Natación", products),
            Subcategory(3, "Gimnasia", products)
        )
    )
    // ... otras categorías
)


@Composable
fun CatalogueNavScreen(categories: List<Category>, navController: NavController) {
    val navControllerCatalogue = rememberNavController()
    NavHost(navControllerCatalogue, startDestination = "screen_a") {
        composable("screen_a") {
            CatalogueScreen(categories, navController, navControllerCatalogue)
        }
        composable("screen_b") {
            ProductDetailScreen(navControllerCatalogue)
        }
    }
}
@Composable
fun CatalogueScreen(categories: List<Category>, navController: NavController, navControllerCatalogue: NavController) {
    val customShape =
        RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp, bottomEnd = 32.dp, bottomStart = 32.dp)
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)

        ) {


            // Barra superior
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Catalogue", fontWeight = FontWeight.Bold, fontSize = 20.sp
                )
                Icon(
                    imageVector = Icons.Default.Menu, contentDescription = "Menu"
                )
            }

            // Barra de búsqueda
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Text(text = "Lorem ipsumerat", modifier = Modifier.padding(start = 16.dp))

            // Sección de iconos grandes
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.SpaceEvenly,

                ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_user), // Reemplaza con tu icono
                    contentDescription = "Bike",
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .height(120.dp)
                        .width(100.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFFF5733), // Orange
                                    Color(0xFFFF7F50) // Lighter orange
                                )
                            )
                        )
                        .padding(8.dp)


                )

                Spacer(modifier = Modifier.padding(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_user), // Reemplaza con tu icono
                    contentDescription = "Bike",
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .height(120.dp)
                        .width(100.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFFF5733), // Orange
                                    Color(0xFFFF7F50) // Lighter orange
                                )
                            )
                        )
                        .padding(8.dp)


                )
                Spacer(modifier = Modifier.padding(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_user), // Reemplaza con tu icono
                    contentDescription = "Bike",
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .height(120.dp)
                        .width(100.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFFF5733), // Orange
                                    Color(0xFFFF7F50) // Lighter orange
                                )
                            )
                        )
                        .padding(8.dp)


                )

                Spacer(modifier = Modifier.padding(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_user), // Reemplaza con tu icono
                    contentDescription = "Bike",
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .height(120.dp)
                        .width(100.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFFF5733), // Orange
                                    Color(0xFFFF7F50) // Lighter orange
                                )
                            )
                        )
                        .padding(8.dp)


                )

                // ... otros iconos ...
            }

            LazyColumn {
                items(categories) { category ->
                    category.subcategories.forEach { subcategory ->

                        Text(
                            text = subcategory.name,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .align(Alignment.Start)
                        )
                        LazyRow(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(subcategory.products) { product ->
                                ProductItem(product, navControllerCatalogue)
                            }
                        }
                    }

                }
            }


            // Secciones de subcategorías


            // ... otras subcategorías ...


        }

        BottomNavigationBar(navController = navController, modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(32.dp)
            .clip(customShape))

    }

}


@Composable
fun ProductItem(product: Product, navControllerCatalogue: NavController) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navControllerCatalogue.navigate("screen_b")
            }

    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )

            Text(product.name, fontSize = 14.sp)

        }
    }
}


@Preview(showBackground = true)
@Composable
fun CataloguePreview() {
    CatalogueScreen(categories, rememberNavController(), rememberNavController())

}

@Composable
fun BottomNavigationBar(navController: NavController, modifier: Modifier) {
    BottomNavigation(
        modifier = modifier,
        elevation = 16.dp,
        backgroundColor = Color.White

    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        BottomNavItem.values().forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.label) }
            )
        }
    }
}