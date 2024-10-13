package com.example.marketplacepuj.ui.features.catalogo.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.marketplacepuj.R

@Preview(showBackground = true)
@Composable
fun ProductPreview() {
    ProductDetailScreen(rememberNavController())

}


@Composable
fun ProductDetailScreen(navController: NavController) {


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

                Image(
                    painter = painterResource(id = R.drawable.ic_user), // Reemplaza con tu imagen
                    contentDescription = "Imagen centrada",
                    modifier = Modifier

                        .align(Alignment.Center) // Centra la imagen tanto horizontal como verticalmente
                        .size(200.dp),
                    colorFilter = ColorFilter.tint(Color.White)

                )

            }


            // Parte inferior de la pantalla
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .weight(0.5f)
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(32.dp)

            ) {

                Column {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Your product",
                            style = MaterialTheme.typography.headlineLarge,

                            )
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorite",


                            )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Your product Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "Thumb up"
                        )
                        Icon(
                            imageVector = Icons.Default.Build,
                            contentDescription = "Badge"
                        )
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit"
                        )
                    }


                }


            }

        }
        Box(
            Modifier
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Color(0xFF007AFF))
                .fillMaxWidth(0.8f)
                .height(150.dp)
                .align(Alignment.BottomCenter)


        ) {
            Text(
                text = "$ 15.000",
                style = TextStyle(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    fontSize = 45.sp,

                    ),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp),

                )
        }

        // Botón anclado abajo
        Button(
            onClick = { /* Acción al presionar el botón */ },
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(65.dp)
                .align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFFF7F50), // Naranja claro
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),

            ) {
            Text(text = "Add to Cart", fontSize = 18.sp)
        }


    }


}