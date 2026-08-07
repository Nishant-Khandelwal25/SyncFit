package com.example.syncfit_core.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun <E> ObserveAsEvents(
    events: Flow<E>,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onEvent: (E) -> Unit
) {
    DisposableEffect(lifecycleOwner.lifecycle, events) {
        val job = lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                events.collect(onEvent)
            }
        }
        onDispose { job.cancel() }
    }
}
