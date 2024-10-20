package com.example.marketplacepuj.ui.features.catalogo.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil3.compose.AsyncImage
import com.example.marketplacepuj.BottomNavItem
import com.example.marketplacepuj.R
import com.example.marketplacepuj.ui.features.catalogo.viewmodel.CatalogueViewModel


data class Category(
    val id: Int, val name: String, val subcategories: List<Subcategory>
)

data class Subcategory(
    val id: Int, val name: String, val products: List<Product>
)

// Datos de muestra (dummy data)
data class Product(
    val id: String,
    val name: String,
    val category: String,
    val subCategory: String,
    val imageUrl: String,
    val price: Double,
    val description: String = "",
    val rating: Int = 0
)

val products = listOf(
    Product(
        "1",
        "iPhone 14",
        "Telefonía",
        "Smartphones",
        "https://via.placeholder.com/150",
        999.99
    ),
    Product(
        "1",
        "iPhone 14",
        "Telefonía",
        "Smartphones",
        "https://via.placeholder.com/150",
        999.99
    ),
    Product(
        "1",
        "iPhone 14",
        "Telefonía",
        "Smartphones",
        "https://via.placeholder.com/150",
        999.99
    ),
    Product(
        "1",
        "iPhone 14",
        "Telefonía",
        "Smartphones",
        "https://via.placeholder.com/150",
        999.99
    ),
    Product(
        "1",
        "iPhone 14",
        "Telefonía",
        "Smartphones",
        "https://via.placeholder.com/150",
        999.99
    ),
    Product(
        "1",
        "iPhone 14",
        "Telefonía",
        "Smartphones",
        "https://via.placeholder.com/150",
        999.99
    ),
    Product(
        "1",
        "iPhone 14",
        "Telefonía",
        "Smartphones",
        "https://via.placeholder.com/150",
        999.99
    ),
    Product(
        "1",
        "iPhone 14",
        "Telefonía",
        "Smartphones",
        "https://via.placeholder.com/150",
        999.99
    ),
    Product(
        "1",
        "iPhone 14",
        "Telefonía",
        "Smartphones",
        "https://via.placeholder.com/150",
        999.99
    ),
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
fun CatalogueNavScreen(navController: NavController, viewModel: CatalogueViewModel) {
    val categories = viewModel.categories
    val navControllerCatalogue = rememberNavController()
    NavHost(navControllerCatalogue, startDestination = "screen_a") {
        composable("screen_a") {
            CatalogueScreen(
                categories,
                navController,
                navControllerCatalogue,
                viewModel.selectedCategory.value,
                viewModel.search, {
                    viewModel.onValueChangedSearch(it)
                },
                {
                    viewModel.onSearch()
                }

            ) {
                viewModel.filterByCategory(it)
            }
        }
        composable("screen_b/{productId}", arguments = listOf(navArgument("productId") {
            type = NavType.StringType
        })) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            val product = viewModel.getProductDetail(productId)
            product?.let {
                ProductDetailScreen(navControllerCatalogue, it) {
                    viewModel.addToCartItem(it)
                    navControllerCatalogue.popBackStack()
                }
            }
        }
    }
}

@Composable
fun CatalogueScreen(
    categories: List<Category>,
    navController: NavController,
    navControllerCatalogue: NavController,
    selectedCategory: String,
    searchText: String,
    onValueChangeSearch: (String) -> Unit,
    onSearch: () -> Unit,
    onFilterCategory: (String) -> Unit,

    ) {
    val customShape =
        RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp, bottomEnd = 32.dp, bottomStart = 32.dp)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.White, // Orange
                        Color(0xFFF2F2F2) // Lighter orange
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 72.dp)
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
                    text = "Catálogo", fontWeight = FontWeight.Bold, fontSize = 20.sp
                )
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    Modifier.clickable { }
                )
            }

            // Barra de búsqueda

            SearchTextField(
                query = searchText,
                onQueryChanged = onValueChangeSearch,
                onSearch = onSearch,
            )


            Text(
                text = "Categorías",
                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
            )

            // Sección de iconos grandes
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp),

                ) {


                CategoryItem(
                    R.drawable.accesorios,
                    "Accesorios",
                    onFilterCategory,
                    selectedCategory
                )
                CategoryItem(R.drawable.papeleria, "Papelería", onFilterCategory, selectedCategory)
                CategoryItem(R.drawable.tshirt, "Ropa", onFilterCategory, selectedCategory)
                CategoryItem(
                    R.drawable.tecnologia,
                    "Tecnología",
                    onFilterCategory,
                    selectedCategory
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


        }

        BottomNavigationBar(
            navController = navController, modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .clip(customShape)
        )

    }

}

@Composable
fun SearchTextField(
    query: String,
    onQueryChanged: (String) -> Unit,
    onSearch: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = query,
        onValueChange = { onQueryChanged(it) },
        placeholder = { Text(text = "Buscar") },

        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .clip(RoundedCornerShape(16.dp)),
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                onSearch()
            }

        ),
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = {
                    onQueryChanged("")
                    onSearch()
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = "Clear search"
                    )
                }
            }
        }
    )
}

@Composable
fun CategoryItem(
    icono: Int,
    categoryName: String,
    onFilterCategory: (String) -> Unit,
    selectedCategory: String
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .size(width = 100.dp, height = 120.dp)
            .clickable { onFilterCategory(categoryName) },

        ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = if (selectedCategory == categoryName) {
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF243484), Color(0xFF2be4dc)
                            )
                        )
                    } else {
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFFF5733), // Orange
                                Color(0xFFFF7F50) // Lighter orange
                            )
                        )
                    }
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Center
                ) {
                    Icon(
                        painter = painterResource(icono), // Reemplaza con tu icono
                        contentDescription = "Bike",
                        modifier = Modifier
                            .size(45.dp),
                        tint = Color(0xFFFF5733)
                    )
                }

                Text(
                    categoryName,
                    fontSize = 12.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(4.dp)
                )
            }


        }


    }


}


@Composable
fun ProductItem(product: Product, navControllerCatalogue: NavController) {

    Card(
        modifier = Modifier
            .size(width = 100.dp, height = 120.dp)
            .clickable {
                navControllerCatalogue.navigate("screen_b/${product.id}")
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        listOf(Color(0xFF2be4dc), Color(0xFF243484))
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .align(Center)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Text(
                    product.name,
                    fontSize = 12.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(4.dp)
                )

            }

        }

    }
}


@Preview(showBackground = true)
@Composable
fun CataloguePreview() {
    CatalogueScreen(
        categories,
        rememberNavController(),
        rememberNavController(),
        "",
        "",
        {},
        {},
        {})

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

