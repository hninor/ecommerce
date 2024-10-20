package com.example.marketplacepuj.ui.features.catalogo.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import by.alexandr7035.banking.ui.components.decoration.SkeletonShape
import by.alexandr7035.banking.ui.components.header.ScreenHeader
import coil3.compose.AsyncImage
import com.example.marketplacepuj.R
import com.example.marketplacepuj.ui.features.payment.HomeIntent
import com.example.marketplacepuj.ui.features.payment.HomeState
import com.example.marketplacepuj.ui.features.payment.PaymentViewModel
import com.example.marketplacepuj.ui.theme.primaryFontFamily
import com.example.marketplacepuj.util.ErrorFullScreen
import com.example.marketplacepuj.util.PrimaryCard
import com.example.marketplacepuj.util.SecondaryToolBar
import com.example.marketplacepuj.util.UiText
import org.koin.androidx.compose.koinViewModel


@Composable
fun OpinionScreen(
    viewModel: PaymentViewModel = koinViewModel(),
    navController: NavController
) {

    LaunchedEffect(Unit) {
        viewModel.emitIntent(HomeIntent.EnterScreen)
    }

    val state = viewModel.state.collectAsStateWithLifecycle().value
    when (state) {
        is HomeState.Success -> OpinionScreen_Ui(
            navController,
            products
        )

        is HomeState.Loading -> PaymentScreen_Skeleton()
        is HomeState.Error -> ErrorFullScreen(error = state.error, onRetry = {
            viewModel.emitIntent(HomeIntent.EnterScreen)
        })
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OpinionScreen_Ui(
    navController: NavController,
    productos: List<Product>
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
            navController.popBackStack()
        }, title = UiText.StringResource(R.string.mis_opiniones))



        Text(
            text = stringResource(id = R.string.opina),
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
        )


        LazyColumn(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {

            items(productos) {
                ProductOpinionItem(it)
            }
        }


    }

}


@Preview(showBackground = true)
@Composable
fun OpinionPreview() {
    OpinionScreen_Ui(
        rememberNavController(), products
    )

}


@Composable
fun ProductOpinionItem(product: Product) {


    var rating by remember { mutableStateOf(0f) }

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
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .weight(2f)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.Start
                ) {


                    Text(
                        text = "Melodica", style = TextStyle(

                            fontFamily = primaryFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    )


                    Text(
                        text = "Comprado el 27 de Marzo", style = TextStyle(
                            color = MaterialTheme.colorScheme.secondary,
                        )
                    )


                }
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                StarRatingBar(
                    maxStars = 5,
                    rating = rating,
                    onRatingChanged = {
                        rating = it
                    }
                )
            }



        }
    }


}


@Composable
fun StarRatingBar(
    maxStars: Int = 5,
    rating: Float,
    onRatingChanged: (Float) -> Unit
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
                            onRatingChanged(i.toFloat())
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


@Composable
private fun PaymentScreen_Skeleton() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(bottom = 24.dp)
    ) {
        ScreenHeader(
            toolbar = {}, panelVerticalOffset = 24.dp
        ) {

        }

        Spacer(Modifier.height(8.dp))



        SkeletonShape(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .width(300.dp)
                .height(200.dp)
        )

        Spacer(Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            List(3) {
                SkeletonShape(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                )
            }
        }
    }
}



