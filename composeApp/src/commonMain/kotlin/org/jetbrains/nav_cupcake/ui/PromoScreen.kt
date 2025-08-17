package org.jetbrains.nav_cupcake.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.nav_cupcake.ui.theme.CupcakeTheme

@Composable
fun PromoScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Закажите сейчас и получите скидку")
    }
}

//@Preview
//@Composable
//fun PromoScreenPreview() {
//    CupcakeTheme {
//        PromoScreen()
//    }
//}