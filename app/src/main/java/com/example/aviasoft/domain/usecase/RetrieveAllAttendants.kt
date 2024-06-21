package com.example.aviasoft.domain.usecase

import com.example.aviasoft.domain.respositotory.SkyInfoRepository

class RetrieveAllAttendants(
    private val skyInfoRepository: SkyInfoRepository
) {

    suspend operator fun invoke() =
        skyInfoRepository.retrievingAttendantsList()

}