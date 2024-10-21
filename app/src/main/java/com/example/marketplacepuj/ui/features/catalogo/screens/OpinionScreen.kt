package com.example.marketplacepuj.ui.features.catalogo.screens

import android.annotation.SuppressLint
import android.text.format.DateFormat
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.marketplacepuj.R
import com.example.marketplacepuj.ui.theme.primaryFontFamily
import com.example.marketplacepuj.util.PrimaryCard
import com.example.marketplacepuj.util.SecondaryToolBar
import com.example.marketplacepuj.util.UiText
import java.util.Date


@Composable
fun OpinionScreen(
    navController: NavController,
    productos: List<Product>,
    fechaCompra: Date,
    onRatingChanged: (idProducto: String, rating: Int) -> Unit
) {



    OpinionScreen_Ui(
        navController,
        productos,
        fechaCompra,
        onRatingChanged

    )

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OpinionScreen_Ui(
    navController: NavController,
    productos: List<Product>,
    fechaCompra: Date,
    onRatingChanged: (idProducto: String, rating: Int) -> Unit
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


        ) {

        SecondaryToolBar(onBack = {
            navController.navigateUp()
        }, title = UiText.StringResource(R.string.mis_opiniones))



        Text(
            text = stringResource(id = R.string.opina),
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
        )


        LazyColumn(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {

            items(productos) {
                ProductOpinionItem(it, fechaCompra, onRatingChanged)
            }
        }


    }

}


@Preview(showBackground = true)
@Composable
fun OpinionPreview() {
    OpinionScreen_Ui(
        rememberNavController(), products, Date()
    ) { idProducto, rating ->


    }

}


@Composable
fun ProductOpinionItem(
    product: Product,
    date: Date,
    onRatingChanged: (idProducto: String, rating: Int) -> Unit
) {


    val day = DateFormat.format("dd", date) as String // 20
    val monthString = DateFormat.format("MMM", date) as String // Jun

    PrimaryCard(
        modifier = Modifier.padding(horizontal = 24.dp),
        paddingValues = PaddingValues(vertical = 16.dp),
        corners = 16.dp
    ) {

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .weight(2f)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.Start
                ) {


                    Text(
                        text = product.name, style = TextStyle(
                            fontFamily = primaryFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    )


                    Text(
                        text = "Comprado el ${day} de ${monthString}", style = TextStyle(
                            color = MaterialTheme.colorScheme.secondary,
                        )
                    )


                }
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                StarRatingBar(
                    maxStars = 5,
                    rating = product.rating.value,
                    onRatingChanged = {
                        if (it != product.rating.value) {
                            onRatingChanged(product.id, it)
                        }

                    }
                )
            }


        }
    }


}


@Composable
fun StarRatingBar(
    maxStars: Int = 5,
    rating: Int,
    onRatingChanged: (Int) -> Unit
) {
    val density = LocalDensity.current.density
    val starSize = (12f * density).dp
    val starSpacing = (0.5f * density).dp

    Row(
        modifier = Modifier.selectableGroup(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon = if (isSelected) Icons.Filled.Star else Icons.Default.Star
            val iconTintColor = if (isSelected) Color(0xFFFFC700) else Color.LightGray
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = Modifier
                    .selectable(
                        selected = isSelected,
                        onClick = {
                            onRatingChanged(i)
                        }
                    )
                    .width(starSize)
                    .height(starSize)
            )

            if (i < maxStars) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }
    }
}





