package com.example.aviasoft.presentation.mainscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aviasoft.data.network.authkey.AuthorizationGen
import com.example.aviasoft.presentation.screens.AttendantDetailScreen
import com.example.aviasoft.presentation.screens.MainScreenAttendsList
import com.example.aviasoft.presentation.ui.theme.AviaSoftTheme
import org.koin.android.ext.android.inject
import java.time.Instant

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AviaSoftTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp)
                ) {
                    val state by viewModel.viewState.collectAsStateWithLifecycle()
                    //navigation components can be implemented
                    if (state.listScreen) {
                        MainScreenAttendsList(state, viewModel::handleIntent)
                    } else {
                        AttendantDetailScreen(state.chosenAttendant, viewModel::handleIntent)
                    }
                }
            }
        }

        val timeStamp = Instant.now().epochSecond

        println("Generated timeStamp : $timeStamp")
        println("Generated token is : " + AuthorizationGen.genkey(timeStamp))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AviaSoftTheme {

    }
}