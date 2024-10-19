package com.example.marketplacepuj.ui.features.catalogo.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PaymentScreen(
    navController: NavController,
    orderItems: List<OrderItem>,
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
        verticalArrangement = Arrangement.SpaceBetween,
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
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }

        Text(text = "Información de la tarjeta", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(text = "Nombre del titular") })
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(text = "Número de tarjeta") })

        // Campos para la fecha de vencimiento y código de seguridad
        // ... (similar a los campos anteriores)

        // Botón para confirmar el pago
        Button(
            onClick = {
                // Validar datos y enviar a un servicio de pago
                // ...
            }
        ) {
            Text(text = "Pagar")
        }


    }

}


@Preview(showBackground = true)
@Composable
fun PaymentPreview() {
    PaymentScreen(rememberNavController(), orderItems)

}


