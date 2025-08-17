package org.jetbrains.nav_cupcake

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import cupcake.composeapp.generated.resources.Res
import cupcake.composeapp.generated.resources.back_button
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.jetbrains.compose.resources.stringResource

enum class AppBarNavIconType {
    NONE,
    BACK,
    CLOSE
}

data class AppBarState(
    val title: String = "",
    val navIconType: AppBarNavIconType = AppBarNavIconType.NONE,
    val onNavIconClick: (() -> Unit)? = null
)

class AppBarViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(value = AppBarState())
    val uiState: StateFlow<AppBarState> = _uiState.asStateFlow()

    fun setState(newState: AppBarState) {
        _uiState.update { currentState ->
            newState
        }
    }
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@Composable
fun CupcakeAppBar(
    viewModel: AppBarViewModel,
    modifier: Modifier = Modifier
) {
    val state = viewModel.uiState.collectAsState().value
    TopAppBar(
        title = { Text(state.title) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            when (state.navIconType) {
                AppBarNavIconType.BACK -> {
                    IconButton(onClick = { state.onNavIconClick?.invoke() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.back_button)
                        )
                    }
                }

                AppBarNavIconType.CLOSE -> {
                    IconButton(onClick = { state.onNavIconClick?.invoke() }) {
                        Icon(Icons.Default.Close, contentDescription = "Закрыть")
                    }
                }

                AppBarNavIconType.NONE -> Unit
            }
        }
    )
}