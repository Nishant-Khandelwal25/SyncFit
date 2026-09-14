package com.example.features.exerciseinfo.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.features.R
import com.example.features.exerciseinfo.viewmodel.ExerciseInfoUiEvent
import com.example.features.exerciseinfo.viewmodel.ExerciseInfoViewModel
import com.example.syncfit_core.navigation.Navigator
import com.example.syncfit_core.navigation.Routes
import com.example.syncfit_core.ui.components.SyncFitResourceImage
import com.example.syncfit_core.ui.components.SyncFitText
import com.example.syncfit_core.ui.theme.Spacing
import com.example.syncfit_core.ui.theme.SyncFitTypography
import com.example.syncfit_core.ui.theme.ToolbarHeight.collapsedHeight
import com.example.syncfit_core.ui.theme.ToolbarHeight.expandedHeight
import com.example.syncfit_core.viewmodel.ObserveAsEvents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseInfoRootView(
    viewModel: ExerciseInfoViewModel,
    workoutName: String,
    exerciseValue: String,
    navigator: Navigator,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    LaunchedEffect(exerciseValue) {
        viewModel.loadExerciseInfo(exerciseValue)
    }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ExerciseInfoUiEvent.LaunchAIFormCheck -> {
                navigator.navigate(Routes.AIFormCheck(event.exerciseName))
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    SyncFitText(
                        Modifier.fillMaxWidth(),
                        text = workoutName,
                        textStyle = SyncFitTypography.titleLarge,
                    )
                },
                navigationIcon = {
                    SyncFitResourceImage(
                        modifier = Modifier
                            .padding(horizontal = Spacing.sm)
                            .size(28.dp)
                            .clickable {
                                navigator.onBack()
                            },
                        resId = R.drawable.ic_back,
                    )
                },
                scrollBehavior = scrollBehavior,
                collapsedHeight = collapsedHeight,
                expandedHeight = expandedHeight,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                ),
            )
        },
    ) { paddingValues ->
        ExerciseInfoScreen(Modifier.padding(paddingValues), state, viewModel::onAction)
    }

}
