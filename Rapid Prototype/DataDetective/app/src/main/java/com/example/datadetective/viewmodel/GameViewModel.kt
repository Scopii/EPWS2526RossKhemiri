package com.example.datadetective.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.datadetective.data.Task
import com.example.datadetective.data.TaskGenerator
import com.example.datadetective.data.UserProfile
import com.example.datadetective.data.sampleDataSet

class GameViewModel : ViewModel() {
    var userProfile by mutableStateOf(UserProfile("Karim"))
        private set
    var currentTask by mutableStateOf<Task?>(null)
        private set
    var selectedAnswerIndex by mutableStateOf<Int?>(null)

    init {
        nextQuestion()
    }
    fun nextQuestion() {
        val base = sampleDataSet.random()

        currentTask = TaskGenerator.generate(base)
        selectedAnswerIndex = null
    }
    fun selectAnswer(index: Int){
        selectedAnswerIndex= index
    }
    fun submitAnswer() {
        val task = currentTask ?: return
        val selected = selectedAnswerIndex ?: return
        if (selected == task.correctOptionIndex) {
            addXp(50)
        }
    }

    private fun addXp(amount: Int) {
        // XP Test
        val newXp = userProfile.xp + amount
        val newLevel = (newXp / 100) + 1
        userProfile = userProfile.copy(xp = newXp, level = newLevel)
    }
}