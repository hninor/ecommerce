package com.example.marketplacepuj.ui.features.catalogo.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CartScreen(navController: NavController) {

    val customShape =
        RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp, bottomEnd = 32.dp, bottomStart = 32.dp)
    val items = listOf(
        CartItem("Lorem ipsum dolor sit", "$1000.00"),
        CartItem("Lorem ipsum dolor sit", "$1000.00"),
        CartItem("Lorem ipsum dolor sit", "$1000.00"),
        CartItem("Lorem ipsum dolor sit", "$1000.00")
    )

    Scaffold(
        bottomBar = {

            BottomNavigationBar(
                navController = navController, modifier = Modifier
                    .padding(32.dp)
                    .clip(customShape)
            )


        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        listOf(Color(0xFF2be4dc), Color(0xFF243484))
                    )
                )
        ) {
            Text(
                text = "Cart",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(16.dp),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items) { item ->
                    CartItemRow(item = item)
                }

                item {
                    // Total
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total:",
                            style = MaterialTheme.typography.h6,
                            color = Color.White
                        )
                        Text(
                            text = "$4000.00",
                            style = MaterialTheme.typography.h6,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Botón Checkout
                    Button(
                        onClick = { /* Handle checkout click */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFFF7F50), // Naranja claro
                            contentColor = Color.White
                        ),
                    ) {
                        Text(text = "Checkout")
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemRow(item: CartItem) {
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
            Icon(
                Icons.Default.Person,
                contentDescription = "Product image",
                modifier = Modifier.padding(16.dp)
            )

            Column {
                Text(text = item.name, fontWeight = FontWeight.Bold)
                Text(text = item.price, color = Color.Gray, fontWeight = FontWeight.Bold)
            }
            IconButton(onClick = { /* Handle remove item */ }) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Remove",
                    tint = Color.Gray
                )


            }
        }

    }
}

data class CartItem(val name: String, val price: String)

@Preview(showBackground = true)
@Composable
fun CartPreview() {
    CartScreen(rememberNavController())

}


