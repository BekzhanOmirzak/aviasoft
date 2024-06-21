package com.example.aviasoft.presentation.mainscreen

import com.example.aviasoft.domain.model.Attendant

sealed interface MainIntent {

    data class OpenDetailScreen(val chooseAttendant: Attendant) : MainIntent

    data object BackFromDetailScreen : MainIntent
}