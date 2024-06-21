package com.example.aviasoft.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<S, E, I>(initialState: S) : ViewModel() {

    protected val _viewState = MutableStateFlow(initialState)
    val viewState = _viewState.asStateFlow()

    private val _event = MutableSharedFlow<E>(1)
    val event = _event.asSharedFlow()

}