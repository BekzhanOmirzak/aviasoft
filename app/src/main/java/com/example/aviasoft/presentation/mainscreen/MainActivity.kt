package com.example.aviasoft.presentation.mainscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.aviasoft.data.network.authkey.AuthorizationGen
import com.example.aviasoft.presentation.ui.theme.AviaSoftTheme
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.inject
import java.time.Instant

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel
        setContent {
            AviaSoftTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->

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