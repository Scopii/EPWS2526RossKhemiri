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

    //Speicherung für Magierachievment und MasteryChart
    val solvedByManipulation: Map<ManipulationType, Int> = emptyMap(),
    val failedByManipulation: Map<ManipulationType, Int> = emptyMap(),

    // Für Snapshots für Tendenz-Vergleiche
    val snapshotXp: Int = 0,
    val snapshotLevel: Int = 1,
    val snapshotSolved: Int = 0,
    val snapshotDone: Int = 0,
    val snapshotHighestStreak: Int = 0,

    val snapshotSolvedByManipulation: Map<ManipulationType, Int> = emptyMap(),
    val snapshotFailedByManipulation: Map<ManipulationType, Int> = emptyMap(),
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
        //Speicherung der Manipulationstypen in PreferenceKeys
        fun solvedKey(type: ManipulationType) = intPreferencesKey("solved_${type.name}")
        fun failedKey(type: ManipulationType) = intPreferencesKey("failed_${type.name}")

        val SNAP_XP = intPreferencesKey("snap_xp")
        val SNAP_LEVEL = intPreferencesKey("snap_level")
        val SNAP_SOLVED = intPreferencesKey("snap_solved")
        val SNAP_DONE = intPreferencesKey("snap_done")
        val SNAP_HIGHEST_STREAK = intPreferencesKey("snap_highest_streak")
        fun snapSolvedKey(type: ManipulationType) = intPreferencesKey("snap_solved_${type.name}")
        fun snapFailedKey(type: ManipulationType) = intPreferencesKey("snap_failed_${type.name}")
    }

    val userProfile: Flow<UserProfile> = context.dataStore.data.map { prefs ->
        val solvedMap = ManipulationType.entries.associateWith { //liest anzahl korrekt gelöster aufgaben pro typ
            prefs[PreferencesKeys.solvedKey(it)] ?: 0 }

        val failedMap = ManipulationType.entries.associateWith {
            prefs[PreferencesKeys.failedKey(it)] ?: 0 }

        val snapSolvedMap = ManipulationType.entries.associateWith {
            prefs[PreferencesKeys.snapSolvedKey(it)] ?: 0
        }

        val snapFailedMap = ManipulationType.entries.associateWith {
            prefs[PreferencesKeys.snapFailedKey(it)] ?: 0
        }

        UserProfile(
            name = "Karim",
            xp = prefs[PreferencesKeys.XP] ?: 0,
            level = prefs[PreferencesKeys.LEVEL] ?: 1,
            titel = prefs[PreferencesKeys.TITEL],
            solvedQuestions = prefs[PreferencesKeys.SOLVED_QUESTIONS] ?: 0,
            doneQuestions = prefs[PreferencesKeys.DONE_QUESTIONS] ?: 0,
            currentStreak = prefs[PreferencesKeys.CURRENT_STREAK] ?: 0,
            highestStreak = prefs[PreferencesKeys.HIGHEST_STREAK] ?: 0,
            solvedByManipulation = solvedMap,
            failedByManipulation = failedMap,

            snapshotXp = prefs[PreferencesKeys.SNAP_XP] ?: 0,
            snapshotLevel = prefs[PreferencesKeys.SNAP_LEVEL] ?: 1,
            snapshotSolved = prefs[PreferencesKeys.SNAP_SOLVED] ?: 0,
            snapshotDone = prefs[PreferencesKeys.SNAP_DONE] ?: 0,
            snapshotHighestStreak = prefs[PreferencesKeys.SNAP_HIGHEST_STREAK] ?: 0,
            snapshotSolvedByManipulation = snapSolvedMap,
            snapshotFailedByManipulation = snapFailedMap,
        )
    }

    // Eine Funktion für alles nach einer Antwort
    suspend fun submitAnswer(correctAnswer: Boolean, manipulationType: ManipulationType?, xpGained: Int) {
        context.dataStore.edit { prefs ->
            // Fragen-Counter
            val done = (prefs[PreferencesKeys.DONE_QUESTIONS] ?: 0) + 1
            prefs[PreferencesKeys.DONE_QUESTIONS] = done

            if (xpGained > 0) {
                // XP & Level
                val currentXp = prefs[PreferencesKeys.XP] ?: 0
                val newXp = currentXp + xpGained
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
            //Zähler für jeweiligen Manipulationstypen
            manipulationType?.let { type ->
                val key =
                    if (correctAnswer)
                        PreferencesKeys.solvedKey(type)
                    else
                        PreferencesKeys.failedKey(type)
                prefs[key] = (prefs[key] ?: 0) + 1
            }

            // Snapshot alle Paar Aufgaben
            if (done % 10 == 0) {
                prefs[PreferencesKeys.SNAP_XP] = prefs[PreferencesKeys.XP] ?: 0
                prefs[PreferencesKeys.SNAP_LEVEL] = prefs[PreferencesKeys.LEVEL] ?: 1
                prefs[PreferencesKeys.SNAP_SOLVED] = prefs[PreferencesKeys.SOLVED_QUESTIONS] ?: 0
                prefs[PreferencesKeys.SNAP_DONE] = prefs[PreferencesKeys.DONE_QUESTIONS] ?: 0
                prefs[PreferencesKeys.SNAP_HIGHEST_STREAK] = prefs[PreferencesKeys.HIGHEST_STREAK] ?: 0
                ManipulationType.entries.forEach { type ->
                    prefs[PreferencesKeys.snapSolvedKey(type)] = prefs[PreferencesKeys.solvedKey(type)] ?: 0
                }
                ManipulationType.entries.forEach { type ->
                    prefs[PreferencesKeys.snapFailedKey(type)] = prefs[PreferencesKeys.failedKey(type)] ?: 0
                }
            }
    } }

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