package com.example.datadetective.data

import java.time.LocalDate

enum class DailyCondition {
    QUESTIONS_SOLVED,
    QUESTIONS_DONE,
    HIGHEST_STREAK,
    ALL_MANIPULATIONS_SOLVED;
    fun currentValue(profile: UserProfile): Int {
        return when (this) {
            QUESTIONS_SOLVED -> profile.dailySolved
            QUESTIONS_DONE -> profile.dailyDone
            HIGHEST_STREAK -> profile.dailyBestStreak
            ALL_MANIPULATIONS_SOLVED ->
                profile.solvedByManipulation.count { it.value > 0 }
        }
    }
}
data class DailyChallenge (

    val id: String,
    val hint: String,
    val condition: DailyCondition,
    val required: Int,
    val xp: Int,
)

val dailyPool = listOf(
    DailyChallenge(
        id = "1",
        hint = "Spiele 5 Aufgaben",
        condition = DailyCondition.QUESTIONS_DONE,
        required = 5,
        xp = 50
    ),
    DailyChallenge(
        id = "2",
        hint = "Löse 2 Aufgaben hintereinander!",
        condition = DailyCondition.HIGHEST_STREAK,
        required = 2,
        xp = 100
    ),
    /*DailyChallenge(
        id = "3",
        hint = "Löse eine Aufgabe pro Manipulation", condition = DailyCondition.ALL_MANIPULATIONS_SOLVED,
        required = ManipulationType.entries.size,
        xp = 150
    ),*/
    DailyChallenge(
        id = "4",
        hint = "Löse 3 Aufgaben",
        condition = DailyCondition.QUESTIONS_SOLVED,
        required = 3,
        xp = 100
    ),
    DailyChallenge(
        id = "5",
        hint = "Löse 3 Aufgaben hintereinander!",
        condition = DailyCondition.HIGHEST_STREAK,
        required = 3,
        xp = 150
    )
)
