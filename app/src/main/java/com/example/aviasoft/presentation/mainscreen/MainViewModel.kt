package com.example.aviasoft.presentation.mainscreen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.aviasoft.domain.respositotory.SkyInfoRepository
import com.example.aviasoft.domain.usecase.RetrieveAllAttendants
import com.example.aviasoft.presentation.base.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val skyInfoSaving: SkyInfoRepository,
    private val attendantsUniqueName: RetrieveAllAttendants
) : BaseViewModel<MainState, MainEvent, MainIntent>(MainState()) {

    //it is better to have wrapper for coroutine tasks
    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("alsdj3dsvsd", "Handling exceptions in SkyInfo retrieving : $throwable")
    }

    init {
        viewModelScope.launch(Dispatchers.IO + handler) {
            skyInfoSaving.downloadingSkyInfo()
        }

        viewModelScope.launch {
            skyInfoSaving.downloadAttendants()
            val result = attendantsUniqueName()
            _viewState.update { it.copy(attendantList = result) }
        }
    }

    fun handleIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.OpenDetailScreen -> {
                _viewState.update {
                    it.copy(
                        chosenAttendant = intent.chooseAttendant,
                        listScreen = false
                    )
                }
            }

            is MainIntent.BackFromDetailScreen -> {
                _viewState.update {
                    it.copy(
                        listScreen = true,
                        chosenAttendant = null
                    )
                }
            }
        }
    }


}