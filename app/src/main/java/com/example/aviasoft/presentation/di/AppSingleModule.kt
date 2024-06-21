package com.example.aviasoft.presentation.di

import com.example.aviasoft.data.network.client.SkyInfoClient
import com.example.aviasoft.data.persistence.db.DatabaseManager
import com.example.aviasoft.data.persistence.db.SQLiteOpenDatabase
import com.example.aviasoft.data.persistence.pref.DatastoreImpl
import com.example.aviasoft.data.persistence.pref.PreferenceManager
import com.example.aviasoft.data.repository.SkyInfoInteractorImpl
import com.example.aviasoft.domain.respositotory.SkyInfoRepository
import com.example.aviasoft.domain.usecase.RetrieveAllAttendants
import com.example.aviasoft.presentation.mainscreen.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appSingleModule = module {

    single { SkyInfoClient() }
    factory<SkyInfoRepository> { SkyInfoInteractorImpl(get(), get(), get()) }
    factory { RetrieveAllAttendants(get()) }

    viewModel {
        MainViewModel(
            skyInfoSaving = get(),
            attendantsUniqueName = get()
        )
    }

    single<PreferenceManager> { DatastoreImpl(get()) }
    single<DatabaseManager> { SQLiteOpenDatabase(get()) }
}