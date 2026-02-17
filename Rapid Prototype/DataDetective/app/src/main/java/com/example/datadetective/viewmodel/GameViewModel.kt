package com.example.datadetective.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datadetective.data.ChartData
import com.example.datadetective.data.Manipulation
import com.example.datadetective.data.Task
import com.example.datadetective.data.TaskGenerator
import com.example.datadetective.data.UserData
import com.example.datadetective.data.UserProfile
import com.example.datadetective.data.sampleDataSet
import infoData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
enum class ManipulationMode {
    SINGLE,
    DOUBLE
}
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

    var sessionQuestionCount by mutableStateOf(1)
        private set

    var lastGainedXp by mutableStateOf(0)
        private set

    var currentTask by mutableStateOf<Task?>(null)
        private set

    var infoTasks by mutableStateOf<List<Task>>(emptyList()) //vordefinierte infotasks zu allen manipulations/diagram kombinationen
    var selectedAnswerIndices by mutableStateOf(setOf<Int>())
        private set
    var manipulationMode by mutableStateOf(ManipulationMode.SINGLE) //Aktuell ausgewählter spielmodus
        private set

    //Wird nach auswahl des Schwierigkeitsgrad aufgerufen
    fun startGame(mode: ManipulationMode) {
        manipulationMode = mode
        nextQuestion()
        sessionQuestionCount = 1
    }
    //Generiert neue Aufgabe abhängig vom aktuellen modus
    fun nextQuestion() {
        currentTask = TaskGenerator.generate(sampleDataSet.random(), manipulationMode)
        selectedAnswerIndices = emptySet()
        sessionQuestionCount++
    }
    fun selectAnswer(index: Int) {
        val maxSelections = when (manipulationMode) {
            ManipulationMode.SINGLE -> 1
            ManipulationMode.DOUBLE -> 2
        }

        selectedAnswerIndices = if (index in selectedAnswerIndices) {
            selectedAnswerIndices - index // Deselect
        } else if (selectedAnswerIndices.size < maxSelections) {
            selectedAnswerIndices + index // Select unter Limit
        } else {
            selectedAnswerIndices // Field nicht hinzufügen wenn Limit Max ist
        }
    }
    fun submitAnswer() {
        val task = currentTask ?: return

        val correctAnswers = task.correctOptionIndices
        val selectedCorrect = selectedAnswerIndices.intersect(correctAnswers).size
        val totalCorrect = correctAnswers.size

        // Calculate XP based on difficulty and correctness
        val baseXp = when (manipulationMode) {
            ManipulationMode.SINGLE -> 50
            ManipulationMode.DOUBLE -> 100
        }

        val xpGained = if (totalCorrect == 0) {
            0
        } else {
            val percentage = selectedCorrect.toFloat() / totalCorrect.toFloat()
            (baseXp * percentage).toInt()
        }

        lastGainedXp = xpGained // Speichern für Popup
        val isCorrect = selectedAnswerIndices == correctAnswers

        viewModelScope.launch {
            task.manipulations.forEach { manipulation ->
                userData.submitAnswer(
                    correctAnswer = isCorrect,
                    manipulationType = manipulation.type,
                    xpGained = xpGained,
                )
            }
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
    fun prepareInfoTasks() { //Infotask(s) für where was this used button
        val task = currentTask ?: return
        val chartType = task.chartType

        val tasks = task.manipulations.mapNotNull { manipulation ->

            val example = infoData.firstOrNull {
                chartType in it.supportedChartTypes && manipulation.type in it.supportedManipulations
            } ?: return@mapNotNull null

            Task(
                id = -example.id,
                title = example.title,
                description = example.description,
                chartType = chartType,
                unit = example.unit,
                yValues = example.yValues,
                xData = example.xData,
                options = emptyList(),
                correctOptionIndices = emptySet(),
                manipulations = listOf(
                    Manipulation(
                        type = manipulation.type,
                        intensity = example.fixedIntensity ?: 1f,
                        categoryRange = example.fixedCategoryRange,
                        fixedMinValue = example.fixedMinValue,
                        fixedMaxValue = example.fixedMaxValue)),
                explanation = example.explanation)
        }
        infoTasks = tasks
    }
    fun createInfoTasks(data: ChartData): Task { //infotasks für newsseite

        val manipulations = data.supportedManipulations.map { type ->
            Manipulation(
                type = type,
                intensity = data.fixedIntensity ?: 1f,
                categoryRange = data.fixedCategoryRange,
                fixedMinValue = data.fixedMinValue,
                fixedMaxValue = data.fixedMaxValue)
        }

        return Task(
            id = -data.id,
            title = data.title,
            description = data.description,
            chartType = data.supportedChartTypes.first(),
            unit = data.unit,
            yValues = data.yValues,
            xData = data.xData,
            options = emptyList(),
            correctOptionIndices = emptySet(),
            manipulations = manipulations,
            explanation = data.explanation
        )
    }

}
