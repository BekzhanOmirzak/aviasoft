package com.example.aviasoft.data.mapper

import com.example.aviasoft.data.network.dto.AttendantsDto
import com.example.aviasoft.domain.model.Attendant

fun AttendantsDto.toAttendant() = Attendant(id, code, name, baseId, type, email, language)