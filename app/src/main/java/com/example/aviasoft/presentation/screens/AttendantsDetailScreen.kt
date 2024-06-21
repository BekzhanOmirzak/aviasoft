package com.example.aviasoft.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aviasoft.domain.model.Attendant
import com.example.aviasoft.presentation.mainscreen.MainIntent


@Composable
fun AttendantDetailScreen(
    attendant: Attendant?,
    onIntent: (MainIntent) -> Unit
) {
    BackHandler {
        onIntent(MainIntent.BackFromDetailScreen)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Text(
            text = "Attendant Detail Screen",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            textAlign = TextAlign.Center
        )

        attendant?.let {
            Text("Type : " + attendant.type, fontSize = 20.sp)
            Text("Name :" + attendant.name, fontSize = 20.sp)
            Text("Id :" + attendant.id, fontSize = 20.sp)
            Text("Base Id :" + attendant.baseId, fontSize = 20.sp)
            Text("Email : " + attendant.email, fontSize = 20.sp)
            Text("Language : " + attendant.language, fontSize = 20.sp)
        }
    }
}

@Preview
@Composable
private fun AttendantDetailScreenPreview() {
    Column {
        AttendantDetailScreen(
            attendant = Attendant(
                "1",
                "32d",
                "Alex",
                baseId = "1",
                type = "Aisle",
                email = "alex@mail.com",
                language = "ru"
            )
        ) {

        }
    }
}