package com.example.marketplacepuj.ui.features.catalogo.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.marketplacepuj.BottomNavItem
import com.example.marketplacepuj.ui.features.catalogo.viewmodel.PedidoViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date


val orderItems = listOf(
    OrderItem("Lorem ipsum dolor sit", 1000.0, emptyList(), Date(), ""),
    OrderItem("Lorem ipsum dolor sit", 1000.0, emptyList(), Date(), ""),
    OrderItem("Lorem ipsum dolor sit", 1000.0, emptyList(), Date(), ""),
    OrderItem("Lorem ipsum dolor sit", 1000.0, emptyList(), Date(), ""),
    OrderItem("Lorem ipsum dolor sit", 1000.0, emptyList(), Date(), ""),
    OrderItem("Lorem ipsum dolor sit", 1000.0, emptyList(), Date(), ""),
    OrderItem("Lorem ipsum dolor sit", 1000.0, emptyList(), Date(), ""),
    OrderItem("Lorem ipsum dolor sit", 1000.0, emptyList(), Date(), ""),
    OrderItem("Lorem ipsum dolor sit", 1000.0, emptyList(), Date(), ""),
    OrderItem("Lorem ipsum dolor sit", 1000.0, emptyList(), Date(), ""),
    OrderItem("Lorem ipsum dolor sit", 1000.0, emptyList(), Date(), ""),
    OrderItem("Lorem ipsum dolor sit", 1000.0, emptyList(), Date(), ""),
)

@Composable
fun OrderScreenHost(
    navController: NavController

) {
    val navControllerCart = rememberNavController()
    val pedidoViewModel: PedidoViewModel = koinViewModel()

    NavHost(navControllerCart, startDestination = "orderList") {
        composable("orderList") {
            OrderScreen(navController = navController, orderItems = pedidoViewModel.orderItems) {
                navControllerCart.navigate("orderDetail/${it.id}")
            }
        }


        composable(
            route = "orderDetail/{orderId}",
            arguments = listOf(navArgument("orderId") { type = NavType.StringType })
        ) {
            val orderId = it.arguments?.getString("orderId")!!

            OpinionScreen(
                navControllerCart,
                pedidoViewModel.getProductos(orderId),
                pedidoViewModel.getFechaCompra(orderId)
            ) { idProducto, rating ->
                pedidoViewModel.onRatingChanged(orderId, idProducto, rating)
            }
        }
    }


}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OrderScreen(
    navController: NavController,
    orderItems: List<OrderItem>,
    onItemSelected: (OrderItem) -> Unit

) {


    val customShape =
        RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp, bottomEnd = 32.dp, bottomStart = 32.dp)


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
                        listOf(
                            Color.White, // Orange
                            Color(0xFFF2F2F2) // Lighter orange)
                        )
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
                        tint = Color.Black
                    )
                }

                Text(
                    text = "Orders",
                    style = MaterialTheme.typography.h5,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }


            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(2f)
            ) {
                items(orderItems) { item ->
                    OrderItemRow(item, onItemSelected)
                }


            }


        }
    }
}

@Composable
fun OrderItemRow(item: OrderItem, onItemSelected: (OrderItem) -> Unit) {

    val formatter = NumberFormat.getCurrencyInstance().apply {
        maximumFractionDigits = 0
    }
    val formattedPrice = formatter.format(item.price)

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemSelected(item)
            }


    ) {

        Column(
            modifier = Modifier
                .background(
                    Color(0xFF2be4dc)
                )
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {


                Text(text = item.name, fontWeight = FontWeight.Bold, color = Color.White)
                Text(
                    text = formattedPrice,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 22.sp
                )


            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Box,
                    contentDescription = "Back",
                    tint = Color.White
                )

                Spacer(modifier = Modifier.padding(4.dp))


                Text(text = "${item.productos.size} items", color = Color.White)

                Spacer(modifier = Modifier.padding(16.dp))
                Icon(
                    imageVector = Calendar,
                    contentDescription = "Back",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.padding(4.dp))

                val fecha = SimpleDateFormat("dd.MM.yyyy")

                Text(text = fecha.format(item.fecha), color = Color.White)


            }
        }


    }
}

data class OrderItem(
    val name: String,
    val price: Double,
    val productos: List<Product>,
    val fecha: Date,
    val id: String
)

@Preview(showBackground = true)
@Composable
fun OrderPreview() {
    OrderScreen(navController = rememberNavController(), orderItems = orderItems) {

    }

}


