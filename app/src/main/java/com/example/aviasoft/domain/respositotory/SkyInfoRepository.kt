package com.example.aviasoft.domain.respositotory

import com.example.aviasoft.domain.model.Attendant

interface SkyInfoRepository {

    suspend fun downloadingSkyInfo()

    suspend fun downloadAttendants();

    suspend fun retrievingAttendantsList(): List<Attendant>

}