package com.example.datadetective.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

data class UserProfile(
    val name: String,
    val xp: Int,
    val level: Int,
    val titel: String?,
    val doneQuestions: Int,
    val solvedQuestions: Int,
    val currentStreak: Int,
    val highestStreak: Int,
)

class UserData(private val context: Context) {

    private object PreferencesKeys {
        val XP = intPreferencesKey("xp")
        val LEVEL = intPreferencesKey("level")
        val TITEL = stringPreferencesKey("titel")
        val DONE_QUESTIONS = intPreferencesKey("doneQuestions")
        val SOLVED_QUESTIONS = intPreferencesKey("solvedQuestions")
        val CURRENT_STREAK = intPreferencesKey("currentStreak")
        val HIGHEST_STREAK = intPreferencesKey("highestStreak")
    }

    val userProfile: Flow<UserProfile> = context.dataStore.data.map { prefs ->
        UserProfile(
            name = "Karim",
            xp = prefs[PreferencesKeys.XP] ?: 0,
            level = prefs[PreferencesKeys.LEVEL] ?: 1,
            titel = prefs[PreferencesKeys.TITEL],
            solvedQuestions = prefs[PreferencesKeys.SOLVED_QUESTIONS] ?: 0,
            doneQuestions = prefs[PreferencesKeys.DONE_QUESTIONS] ?: 0,
            currentStreak = prefs[PreferencesKeys.CURRENT_STREAK] ?: 0,
            highestStreak = prefs[PreferencesKeys.HIGHEST_STREAK] ?: 0,
        )
    }

    // Eine Funktion für alles nach einer Antwort
    suspend fun submitAnswer(correctAnswer: Boolean) {
        context.dataStore.edit { prefs ->
            // Fragen-Counter
            val done = (prefs[PreferencesKeys.DONE_QUESTIONS] ?: 0) + 1
            prefs[PreferencesKeys.DONE_QUESTIONS] = done

            if (correctAnswer) {
                // XP & Level
                val currentXp = prefs[PreferencesKeys.XP] ?: 0
                val newXp = currentXp + 50
                val newLevel = (newXp / 100) + 1
                prefs[PreferencesKeys.XP] = newXp
                prefs[PreferencesKeys.LEVEL] = newLevel

                // Stats
                val solved = (prefs[PreferencesKeys.SOLVED_QUESTIONS] ?: 0) + 1
                prefs[PreferencesKeys.SOLVED_QUESTIONS] = solved

                // Streak
                val streak = (prefs[PreferencesKeys.CURRENT_STREAK] ?: 0) + 1
                prefs[PreferencesKeys.CURRENT_STREAK] = streak

                val highestStreak = (prefs[PreferencesKeys.HIGHEST_STREAK] ?: 0) + 1
                if (streak >= highestStreak) prefs[PreferencesKeys.HIGHEST_STREAK] = streak
            } else {
                // Streak zurücksetzen bei falscher Antwort
                prefs[PreferencesKeys.CURRENT_STREAK] = 0
            }
        }
    }

    suspend fun setTitel(titel: String?) {
        context.dataStore.edit { prefs ->
            if (titel != null) prefs[PreferencesKeys.TITEL] = titel
            else prefs.remove(PreferencesKeys.TITEL)
        }
    }

    suspend fun resetProgress() {
        context.dataStore.edit { it.clear() }
    }
}