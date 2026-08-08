package com.example.syncfit_core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<S : UiState, A : UiAction, E : UiEvent>(initialState: S) :
    ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    val currentState: S
        get() = _state.value

    private val _events = Channel<E>(capacity = Channel.Factory.BUFFERED)
    val events: Flow<E> = _events.receiveAsFlow()

    fun onAction(action: A) {
        handleAction(action)
    }

    abstract fun handleAction(action: A)

    protected fun setState(reducer: S.() -> S) {
        _state.update(reducer)
    }

    protected fun sendEvent(event: () -> E) {
        viewModelScope.launch {
            _events.send(event())
        }
    }

    protected fun launch(
        onError: (Throwable) -> Unit = {},
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        val handler = CoroutineExceptionHandler { _, throwable -> onError(throwable) }
        return viewModelScope.launch(handler) { block() }
    }
}
