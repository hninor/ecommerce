package com.example.marketplacepuj.util

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.marketplacepuj.ui.theme.MarketplacePUJTheme

@Composable
fun ScreenPreview(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit = {},
) {

    MarketplacePUJTheme(
        darkTheme = darkTheme,
        dynamicColor = false
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            content.invoke()
        }
    }
}