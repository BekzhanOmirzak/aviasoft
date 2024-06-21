package com.example.aviasoft.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aviasoft.domain.model.Attendant
import com.example.aviasoft.presentation.mainscreen.MainIntent
import com.example.aviasoft.presentation.mainscreen.MainState

@Composable
fun MainScreenAttendsList(
    state: MainState,
    onIntent: (MainIntent) -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 20.dp)
    ) {
        Text(text = "Attendant List")
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(state.attendantList) {
                AttendantItem(it, onIntent)
            }
        }
    }
}

@Composable
fun AttendantItem(
    attendant: Attendant,
    onIntent: (MainIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onIntent(MainIntent.OpenDetailScreen(attendant))
            }
    ) {
        Text(attendant.name, fontSize = 20.sp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(color = Color.LightGray)
        )
    }
}

@Preview
@Composable
private fun MainScreenAttendsListPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        MainScreenAttendsList(
            state = MainState(
                attendantList = listOf(
                    Attendant(
                        "1",
                        "32d",
                        "Alex",
                        baseId = "1",
                        type = "Aisle",
                        email = "alex@mail.com",
                        language = "ru"
                    ),
                    Attendant(
                        "1",
                        "32d",
                        "Alex",
                        baseId = "1",
                        type = "Aisle",
                        email = "alex@mail.com",
                        language = "ru"
                    ),
                    Attendant(
                        "1",
                        "32d",
                        "Alex",
                        baseId = "1",
                        type = "Aisle",
                        email = "alex@mail.com",
                        language = "ru"
                    )
                )
            )
        ) {

        }
    }
}