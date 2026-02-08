package com.example.datadetective.data

import androidx.compose.ui.autofill.ContentDataType

enum class Condition {
    HIGHEST_STREAK,
    QUESTIONS_SOLVED,
    QUESTIONS_DONE,
    LOWEST_QUESTION_TIME,
    LEVEL_REACHED
}

data class AchievementData (
    val title: String,
    val hint: String,
    val condition: Condition,
    val required: Int,
)

val achievements = listOf(
    AchievementData(
        title = "Lehrling",
        hint = "Beginne deine Reise",
        condition = Condition.LEVEL_REACHED,
        required = 1,
    ),
    AchievementData(
        title = "Bibliothekar",
        hint = "Löse 3 Aufgaben hintereinander!",
        condition = Condition.HIGHEST_STREAK,
        required = 3
    ),
    AchievementData(
        title = "Gelehrter",
        hint = "Löse 5 Aufgaben hintereinander!", condition = Condition.HIGHEST_STREAK,
        required = 5
    ),
    AchievementData(
        title = "Forscher",
        hint = "Löse 5 Aufgaben!",
        condition = Condition.QUESTIONS_SOLVED,
        required = 5
    ),
    AchievementData(
        title = "Astronaut",
        hint = "Absolviere 10 Aufgaben!",
        condition = Condition.QUESTIONS_DONE,
        required = 10
    ),
    AchievementData(
        title = "Blitz",
        hint = "Löse eine Aufgabe in unter 3 Sekunden",
        condition = Condition.LOWEST_QUESTION_TIME,
        required = 3,
    ),
    AchievementData(
        title = "Sage",
        hint = "Erreiche Level 10",
        condition = Condition.LEVEL_REACHED,
        required = 10,
    ),
    //AchievementData( title = "Magier", hint = "Löse Eine Aufgabe zu jedem Manipulations-Typ"), ?? Wie implementieren
)