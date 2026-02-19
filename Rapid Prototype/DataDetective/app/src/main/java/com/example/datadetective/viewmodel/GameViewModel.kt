package com.example.datadetective.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datadetective.data.ChartData
import com.example.datadetective.data.DailyChallenge
import com.example.datadetective.data.Manipulation
import com.example.datadetective.data.Task
import com.example.datadetective.data.TaskGenerator
import com.example.datadetective.data.UserData
import com.example.datadetective.data.UserProfile
import com.example.datadetective.data.dailyPool
import com.example.datadetective.data.sampleDataSet
import infoData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.random.Random
enum class ManipulationMode {
    SINGLE,
    DOUBLE,
    SURVIVAL //Neuer Spielmodus survival
}
data class SurvivalResult( //Für survival run ende screen
    val streak: Int,
    val best: Int
)
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
    var dailyXpPopup by mutableStateOf<DailyChallenge?>(null)
    var survivalStreak by mutableStateOf(0)
        private set
    var currentTaskMode by mutableStateOf(ManipulationMode.SINGLE)
        private set
    var survivalBest by mutableStateOf(0)
        private set
    var survivalRunResult by mutableStateOf<SurvivalResult?>(null)
        private set
    private var survivalQuestionCount = 0
    //Wird nach auswahl des Schwierigkeitsgrad aufgerufen
    fun startGame(mode: ManipulationMode) {
        manipulationMode = mode
        if (mode == ManipulationMode.SURVIVAL) {
            survivalStreak = 0
            survivalQuestionCount = 0
            survivalBest = userProfile.value.survivalBest
        }
        nextQuestion()
        sessionQuestionCount = 1
    }
    //Generiert neue Aufgabe abhängig vom aktuellen modus
    fun nextQuestion() {
        currentTaskMode = when (manipulationMode) {
            ManipulationMode.SURVIVAL -> {
                survivalQuestionCount++
                if (survivalQuestionCount <= 3) { // Erste 3 aufgaben immer single, danach zufällig ob single/double
                    ManipulationMode.SINGLE
                } else {
                    if (Random.nextBoolean()) ManipulationMode.SINGLE else ManipulationMode.DOUBLE
                }
            }
            else -> manipulationMode
        }
        currentTask = TaskGenerator.generate(sampleDataSet.random(),currentTaskMode)
        selectedAnswerIndices = emptySet()
        sessionQuestionCount++
    }
    fun selectAnswer(index: Int) {
        val maxSelections = when (manipulationMode) {
            ManipulationMode.SINGLE -> 1
            ManipulationMode.DOUBLE -> 2
            ManipulationMode.SURVIVAL -> 2
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
            ManipulationMode.SURVIVAL -> 0
        }

        val xpGained = if (totalCorrect == 0) {
            0
        } else {
            val percentage = selectedCorrect.toFloat() / totalCorrect.toFloat()
            (baseXp * percentage).toInt()
        }

        lastGainedXp = xpGained // Speichern für Popup
        val isCorrect = selectedAnswerIndices == correctAnswers
        //Survivalmode logik
        if (manipulationMode == ManipulationMode.SURVIVAL) {
            if (isCorrect) {
                survivalStreak++
                if (survivalStreak > survivalBest) {
                    survivalBest = survivalStreak
                    // Persistieren im DataStore
                    viewModelScope.launch {
                        userData.saveSurvivalBest(survivalBest)
                    }
                }
            } else {
                survivalRunResult = SurvivalResult(
                    streak = survivalStreak,
                    best = survivalBest
                )
                survivalStreak = 0
                return
            }
        }
        viewModelScope.launch {
            task.manipulations.forEach { manipulation ->
                userData.submitAnswer(
                    correctAnswer = isCorrect,
                    manipulationType = manipulation.type,
                    xpGained = xpGained,
                    normalMode = manipulationMode != ManipulationMode.SURVIVAL
                )
            }
            val profile = userData.userProfile.first()

            var completedSet = profile.dailyCompletedChallenges
            var popup: DailyChallenge? = null

            //Challenges prüfen mit frischen Daten
            todayChallenges.forEach { challenge ->

                val progress = challenge.condition.currentValue(profile)
                if (progress >= challenge.required &&
                    challenge.id !in completedSet) {
                    completedSet = completedSet + challenge.id
                    popup = challenge
                }
            }
            popup?.let {
                dailyXpPopup = it
                userData.grantDailyReward(it)
                userData.saveDailyCompletion(completedSet)
            }
        }
    }
//Nimmt jeden tag neue zufällige challenges aus dem pool
    val todayChallenges: List<DailyChallenge>
        get() {
            val seed = LocalDate.now().toEpochDay().toInt()
            return dailyPool.shuffled(Random(seed)).take(3)
        }
    fun dismissSurvivalResult() {
        survivalRunResult = null
        currentTask = null
    }
    fun setTitel(titel: String?) {
        viewModelScope.launch {
            userData.setTitel(titel)
        }
    }

    fun resetProgress() {
        viewModelScope.launch {
            userData.resetProgress()
            userData.resetDailyStats()
            dailyXpPopup = null

        }
    }
    fun resetDailyChallenges() {
        viewModelScope.launch {
            userData.resetDailyStats()
            dailyXpPopup = null }
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
