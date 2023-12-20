package com.example.stopwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.stopwatch.ui.theme.StopWatchTheme

class MainActivity : ComponentActivity() {
    private val timerViewModel: TimerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StopWatchTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    // BasicCountdownTimer()
                    CountDownTimerWithPause(timerViewModel)
                }
            }
        }
    }
}

@Composable
fun CountDownTimerWithPause(timerViewModel: TimerViewModel) {
    val timeLeft by timerViewModel.timeLeft.collectAsState()
    var isPaused by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = timeLeft, key2 = isPaused) {
        if(!isPaused) {
            timerViewModel.startTimer()
        } else {
            timerViewModel.pauseTimer()
        }
    }

    fun resetTimer() {
        timerViewModel.resetTimer()
        isPaused = false
    }

    Column {
        Text(text = "Time left: $timeLeft")
        Button(onClick = { isPaused = !isPaused }) {
            Text(text = if (isPaused) "Resume" else "Pause")
        }
        Button(onClick = { resetTimer() }) {
            Text(text = "Reset")
        }
    }
}