package com.cheonjaeung.compose.grid.sample.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cheonjaeung.compose.grid.sample.SampleScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleScreen()
        }
    }
}
