package com.example.marketplacepuj.ui.features.catalogo.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import java.text.NumberFormat

@Preview(showBackground = true)
@Composable
fun ProductPreview() {
    ProductDetailScreen(
        rememberNavController(), Product(
            "1",
            "iPhone 14 Full plus Extra",
            "Telefonía",
            "Smartphones",
            "https://via.placeholder.com/150",
            999.99,
            description = "Your product very smart"
        ), {}
    )

}


@Composable
fun ProductDetailScreen(
    navController: NavController,
    product: Product,
    onAddToCartItem: (Product) -> Unit
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Parte superior de la pantalla
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFF5733), // Orange
                            Color(0xFFFF7F50) // Lighter orange
                        )
                    )
                )
        ) {


            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxSize()

            ) {

                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                AsyncImage(
                    model = product.imageUrl, // Reemplaza con tu imagen
                    contentDescription = "Imagen centrada",
                    modifier = Modifier
                        .clip(CircleShape)
                        .align(Alignment.Center) // Centra la imagen tanto horizontal como verticalmente
                        .size(200.dp),
                )

            }


            // Parte inferior de la pantalla
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .weight(0.5f)
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)


            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Column(
                        modifier = Modifier
                            .weight(2f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = product.name,
                                modifier = Modifier.weight(3f),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )

                            Box(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .clip(CircleShape)
                                    .background(Color(0xFFFF7F50))
                                    .weight(1f)
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = "Favorite",
                                    tint = Color.White,
                                    modifier = Modifier.align(Alignment.Center)

                                )
                            }


                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = product.description,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Card {
                                Icon(
                                    imageVector = Icons.Default.ThumbUp,
                                    contentDescription = "Thumb up",
                                    modifier = Modifier.padding(16.dp)
                                )
                            }

                            Card {
                                Icon(
                                    imageVector = Icons.Default.Build,
                                    contentDescription = "Badge",
                                    modifier = Modifier.padding(16.dp)
                                )
                            }

                            Card {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    modifier = Modifier.padding(16.dp)
                                )
                            }

                        }
                    }



                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .align(CenterHorizontally)
                    ) {
                        Box(
                            Modifier
                                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                                .background(Color(0xFF007AFF))
                                .fillMaxWidth(0.8f)
                                .height(130.dp)


                        ) {
                            val formatter = NumberFormat.getCurrencyInstance().apply {
                                maximumFractionDigits = 0
                            }
                            val formattedPrice = formatter.format(product.price)
                            Text(
                                text = formattedPrice,
                                style = TextStyle(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White,
                                    fontSize = 35.sp,

                                    ),
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .padding(top = 16.dp),

                                )
                        }

                        // Botón anclado abajo
                        Button(
                            onClick = { onAddToCartItem(product) },
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(55.dp)
                                .align(Alignment.BottomCenter),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF7F50), // Naranja claro
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),

                            ) {
                            Text(
                                text = "Añadir a la cesta",
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }


                }


            }

        }


    }


}