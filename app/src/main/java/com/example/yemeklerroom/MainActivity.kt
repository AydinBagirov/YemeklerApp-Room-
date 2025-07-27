package com.example.yemeklerroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.yemeklerroom.pages.MainPage
import com.example.yemeklerroom.pages.PageTransitions
import com.example.yemeklerroom.ui.theme.YemeklerRoomTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YemeklerRoomTheme {
                PageTransitions()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YemeklerRoomTheme {
        PageTransitions()
    }
}