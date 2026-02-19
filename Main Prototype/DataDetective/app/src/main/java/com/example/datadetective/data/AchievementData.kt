package com.example.datadetective.data

import androidx.compose.ui.autofill.ContentDataType

enum class Condition {
    HIGHEST_STREAK,
    QUESTIONS_SOLVED,
    QUESTIONS_DONE,
    LOWEST_QUESTION_TIME,
    LEVEL_REACHED,
    ALL_MANIPULATIONS_SOLVED;
    //Berechneten aktuellen Fortschrittswert der Conditions auf Basis des Userprofiles
    fun currentValue(profile: UserProfile): Int {
        return when (this) {
            HIGHEST_STREAK -> profile.highestStreak
            QUESTIONS_SOLVED -> profile.solvedQuestions
            QUESTIONS_DONE -> profile.doneQuestions
            LEVEL_REACHED -> profile.level
            LOWEST_QUESTION_TIME -> 0
            ALL_MANIPULATIONS_SOLVED ->
                ManipulationType.entries.toTypedArray().count { type -> //zählt die Anzahl der manTypen, welche mindestens einmal korrekt gelöst wurden
                    (profile.solvedByManipulation[type] ?: 0) > 0
                }
        }
    }
    //Zielwert für Achievementerreichung
    fun requiredValue(required: Int): Int {
        return when (this) {
            ALL_MANIPULATIONS_SOLVED -> ManipulationType.entries.size //Ziel=die anzahl aller exisitieren ManpulationType-Einträge
            else -> required
        }
    }
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
        hint = "Löse eine Aufgabe in unter 1 Sekunden",
        condition = Condition.LOWEST_QUESTION_TIME,
        required = 1,
    ),
    AchievementData(
        title = "Sage",
        hint = "Erreiche Level 10",
        condition = Condition.LEVEL_REACHED,
        required = 10,
    ),
    AchievementData(
        title = "Magier",
        hint = "Löse Eine Aufgabe zu jedem Manipulations-Typ",
        condition = Condition.ALL_MANIPULATIONS_SOLVED,
        required = 0,
    )
)