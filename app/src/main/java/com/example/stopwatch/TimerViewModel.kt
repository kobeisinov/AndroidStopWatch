package com.example.stopwatch

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TimerViewModel: ViewModel() {
    /*
    * This line declares a private mutable state flow _timeLeft
    * and initializes it with a value of 60.
    * MutableStateFlow is a state holder observable
    * that emits updates to its collectors.
    * It's private because it should only be modified within the ViewModel
    * */
    private val _timeLeft = MutableStateFlow(60)

    /*
    * This line exposes _timeLeft as a read-only StateFlow to the UI.
    * This allows the UI to observe changes to timeLeft but not modify it
    * */
    val timeLeft = _timeLeft.asStateFlow()

    // This Job will be used to control the coroutine that runs the timer.
    private var timerJob: Job? = null

    fun startTimer() {
        // This function starts the timer.
        // It cancels any existing timer job, then launches a new coroutine in the ViewModel's scope.
        // Inside the coroutine, it enters a loop that decrements _timeLeft every second until _timeLeft reaches zero.
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            if (_timeLeft.value > 0) {
                delay(1000L)
                _timeLeft.value--
            }
        }
    }

    fun pauseTimer() {
        // This function pauses the timer.
        // It does this by simply cancelling the timer job,
        // which stops the coroutine that's decrementing _timeLeft.
        timerJob?.cancel()
        timerJob = null
    }

    fun resetTimer() {
        _timeLeft.value = 60
        timerJob?.cancel()
        timerJob = null
    }

    override fun onCleared() {
        // This function is called when the ViewModel is cleared,
        // typically when the UI controller (like an Activity) is finished or destroyed.
        super.onCleared()
        timerJob?.cancel()
    }
}