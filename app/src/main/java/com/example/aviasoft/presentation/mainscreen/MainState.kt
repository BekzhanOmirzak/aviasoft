package com.example.aviasoft.presentation.mainscreen

import com.example.aviasoft.domain.model.Attendant

data class MainState(
    val isLoading: Boolean = false,
    val listScreen: Boolean = true,
    val attendantList: List<Attendant> = emptyList(),
    val chosenAttendant: Attendant? = null
)