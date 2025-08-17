package org.jetbrains.nav_cupcake

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun AppBarHandler(
    navController: NavHostController,
    appBarViewModel: AppBarViewModel,
    getTitle: (String?) -> String,
    iconType: (String?) -> AppBarNavIconType = { AppBarNavIconType.BACK }
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route

    // Автоматически обновляем AppBar
    LaunchedEffect(route) {
        val hasBack = navController.previousBackStackEntry != null
        appBarViewModel.setState(
            AppBarState(
                title = getTitle(backStackEntry?.destination?.route),
                navIconType = if (hasBack) iconType(route) else AppBarNavIconType.NONE,
                onNavIconClick = { navController.popBackStack() }
            )
        )
    }
}