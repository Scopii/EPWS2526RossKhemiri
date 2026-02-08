package com.example.datadetective.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datadetective.data.Task
import com.example.datadetective.data.TaskGenerator
import com.example.datadetective.data.UserData
import com.example.datadetective.data.UserProfile
import com.example.datadetective.data.sampleDataSet
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GameViewModel(
    private val userData: UserData
) : ViewModel() {

    val userProfile = userData.userProfile.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserProfile(
            "Karim",
            0,
            1,
            null,
            0,
            0,
            0,
            0
            )
    )

    var currentTask by mutableStateOf<Task?>(null)
        private set
    var selectedAnswerIndex by mutableStateOf<Int?>(null)
        private set

    init {
        nextQuestion()
    }

    fun nextQuestion() {
        currentTask = TaskGenerator.generate(sampleDataSet.random())
        selectedAnswerIndex = null
    }

    fun selectAnswer(index: Int) {
        selectedAnswerIndex = index
    }

    fun submitAnswer() {
        val task = currentTask ?: return
        val selected = selectedAnswerIndex ?: return
        val isCorrect = selected == task.correctOptionIndex

        viewModelScope.launch {
            userData.submitAnswer(isCorrect) // Eine Funktion!
        }
    }

    fun setTitel(titel: String?) {
        viewModelScope.launch {
            userData.setTitel(titel)
        }
    }

    fun resetProgress() {
        viewModelScope.launch {
            userData.resetProgress()
        }
    }
}