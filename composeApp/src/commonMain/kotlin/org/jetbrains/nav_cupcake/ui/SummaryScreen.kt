/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jetbrains.nav_cupcake.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cupcake.composeapp.generated.resources.Res
import cupcake.composeapp.generated.resources.cancel
import cupcake.composeapp.generated.resources.cupcakes
import cupcake.composeapp.generated.resources.flavor
import cupcake.composeapp.generated.resources.new_cupcake_order
import cupcake.composeapp.generated.resources.order_details
import cupcake.composeapp.generated.resources.pickup_date
import cupcake.composeapp.generated.resources.quantity
import cupcake.composeapp.generated.resources.send
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.nav_cupcake.AppBarHandler
import org.jetbrains.nav_cupcake.AppBarNavIconType
import org.jetbrains.nav_cupcake.AppBarViewModel
import org.jetbrains.nav_cupcake.data.OrderUiState
import org.jetbrains.nav_cupcake.ui.components.FormattedPriceLabel

enum class SummaryNestedScreen(val title: String) {
    MAIN("Summary"),
    ABOUT("About"),
    LEGAL_INFO("Legal Info")
}

@Composable
private fun OrderSummaryScreenContent(
    orderUiState: OrderUiState,
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val numberOfCupcakes = stringResource(
        Res.string.cupcakes, // TODO: plurals
        orderUiState.quantity
    )
    //Load and format a string resource with the parameters.
    val orderSummary = stringResource(
        Res.string.order_details,
        numberOfCupcakes,
        orderUiState.flavor,
        orderUiState.date,
        orderUiState.quantity
    )
    val newOrder = stringResource(Res.string.new_cupcake_order)
    //Create a list of order summary to display
    val items = listOf(
        // Summary line 1: display selected quantity
        Pair(stringResource(Res.string.quantity), numberOfCupcakes),
        // Summary line 2: display selected flavor
        Pair(stringResource(Res.string.flavor), orderUiState.flavor),
        // Summary line 3: display selected pickup date
        Pair(stringResource(Res.string.pickup_date), orderUiState.date)
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items.forEach { item ->
                Text(item.first.uppercase())
                Text(text = item.second, fontWeight = FontWeight.Bold)
                HorizontalDivider(thickness = 1.dp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            FormattedPriceLabel(
                subtotal = orderUiState.price,
                modifier = Modifier.align(Alignment.End)
            )
        }
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onSendButtonClicked(newOrder, orderSummary) }
                ) {
                    Text(stringResource(Res.string.send))
                }
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onCancelButtonClicked
                ) {
                    Text(stringResource(Res.string.cancel))
                }
                ClickableText(
                    text = "Learn more about this order",
                    onClick = { navController.navigate(SummaryNestedScreen.ABOUT.name) }
                )
                ClickableText(
                    text = "Legal Info",
                    onClick = { navController.navigate(SummaryNestedScreen.LEGAL_INFO.name) }
                )
            }
        }
    }
}

@Composable
fun OrderSummaryScreen(
    orderUiState: OrderUiState,
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    appBarViewModel: AppBarViewModel,
    modifier: Modifier = Modifier
) {
    val nestedNavController = rememberNavController()

    AppBarHandler(
        navController = nestedNavController,
        appBarViewModel = appBarViewModel,
        getTitle = { route ->
            SummaryNestedScreen.valueOf(
                route ?: SummaryNestedScreen.MAIN.name
            ).title
        },
        iconType = { route ->
            when (route) {
                SummaryNestedScreen.LEGAL_INFO.name -> AppBarNavIconType.CLOSE
                SummaryNestedScreen.ABOUT.name -> AppBarNavIconType.CLOSE
                else -> AppBarNavIconType.BACK
            }
        }
    )

    NavHost(
        navController = nestedNavController,
        startDestination = SummaryNestedScreen.MAIN.name,
    ) {
        composable(route = SummaryNestedScreen.MAIN.name) {
            OrderSummaryScreenContent(
                orderUiState = orderUiState,
                onCancelButtonClicked = onCancelButtonClicked,
                onSendButtonClicked = onSendButtonClicked,
                navController = nestedNavController,
                modifier = modifier
            )
        }

        composable(route = SummaryNestedScreen.ABOUT.name) {
            AboutOrderScreen()
        }

        composable(route = SummaryNestedScreen.LEGAL_INFO.name) {
            LegalInfoScreen()
        }
    }
}

@Composable
fun ClickableText(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall.copy(
            fontSize = 18.sp,
            textDecoration = TextDecoration.Underline,
            color = Color.Gray,
            textAlign = TextAlign.Center
        ),
        modifier = Modifier.fillMaxWidth().clickable { onClick() }
    )
}