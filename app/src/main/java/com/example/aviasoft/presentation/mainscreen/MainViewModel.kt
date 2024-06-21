package com.example.aviasoft.presentation.mainscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aviasoft.domain.respositotory.SkyInfoInteractor
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val skyInfoSaving: SkyInfoInteractor
) : ViewModel() {

    //it is better to have wrapper for coroutines tasks
    private val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("alsdj3dsvsd", "Handling exceptions in SkyInfo retrieving : $throwable")
    }

    init {
        viewModelScope.launch(Dispatchers.IO + handler) {
            skyInfoSaving.retrievingSkyInfo()
        }
    }
}