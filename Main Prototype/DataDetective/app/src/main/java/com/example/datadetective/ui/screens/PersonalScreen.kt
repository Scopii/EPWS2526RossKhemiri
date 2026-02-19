package com.example.datadetective.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.datadetective.ui.charts.MasteryRadarChart
import com.example.datadetective.ui.charts.masteryByManipulation
import com.example.datadetective.viewmodel.GameViewModel

@Composable
fun UserScreen(viewModel: GameViewModel) {

    val profile = viewModel.userProfile.collectAsState().value

    val masteryByType = remember(profile.solvedByManipulation, profile.failedByManipulation) {
        masteryByManipulation(profile.solvedByManipulation, profile.failedByManipulation)
    }

    val snapshotMastery = remember(profile.snapshotSolvedByManipulation) {
        masteryByManipulation(
            profile.snapshotSolvedByManipulation,
            profile.snapshotFailedByManipulation
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Spacer(Modifier.height(6.dp))
            Text(
                "Personal Stats",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        item {
            SectionCard {
                StatRow {
                    // Level
                    StatValue("Level", profile.level, profile.level - profile.snapshotLevel)
                    // XP
                    StatValue("XP", profile.xp, profile.xp - profile.snapshotXp)
                    // Correct Percentage
                    val curPercentage = if (profile.doneQuestions > 0)
                        (profile.solvedQuestions.toFloat() / profile.doneQuestions.toFloat()) * 100f else 0f
                    val lastPercentage = if (profile.snapshotDone > 0)
                        (profile.snapshotSolved.toFloat() / profile.snapshotDone.toFloat()) * 100f else 0f
                    val percentageDelta = (curPercentage - lastPercentage).toInt() // z.B. +5 oder -3
                    StatValue("Correct", "%.1f%%".format(curPercentage), percentageDelta)
                }
            }
        }



        item {
            SectionCard {
                StatRow {
                    // Current Streak
                    StatValue("Current Streak", profile.currentStreak)
                    // Highest Streak
                    StatValue("Highest Streak", profile.highestStreak, profile.highestStreak - profile.snapshotHighestStreak)
                    //Survival Highest Streak
                    StatValue("Survival Best", profile.survivalBest)
                }
            }
        }

        item {
            SectionCard {
                StatRow {
                    // Solved Questions
                    StatValue("Correct Questions", profile.solvedQuestions, profile.solvedQuestions - profile.snapshotSolved)
                    // Done Questions
                    StatValue("Total Questions", profile.doneQuestions, profile.doneQuestions - profile.snapshotDone)
                }
            }
        }

        //Mastery
        item {
            StatCard(title = "Manipulation Mastery") {
                MasteryRadarChart(
                    mastery = masteryByType,
                    comparisonMastery = snapshotMastery,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
            }
        }

        //Manipulation Streaks
        item {
            StatCard(title = "Manipulation Streaks") {

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                    profile.manipulationCurrentStreaks
                        .forEach { (type, current) ->

                            val best = profile.manipulationBestStreaks[type] ?: 0
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    type.label,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                                    Text(
                                        "Current: $current",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        "Best: $best",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                }
            }
        }

        item { Spacer(Modifier.height(16.dp)) }
    }
}
//Layout hilfen

//Flexible Zeile
@Composable
fun StatRow(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        ) {
        content()
    }
}
//Cards für Werte
@Composable
fun SectionCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 6.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
    }
}
@Composable
fun StatValue(label: String, value: Any, delta: Int? = null) {
    Card(
        modifier = Modifier
            .padding(horizontal = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(label, style = MaterialTheme.typography.labelSmall)
            Text(value.toString(), style = MaterialTheme.typography.bodyLarge)

            if (delta != null && delta != 0) {
                Text(
                    text = if (delta > 0) "+$delta" else "$delta",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (delta > 0) Color(0xFF66BB6A) else Color(0xFFEF5350)
                )
            }
        }
    }
}

//Card für Chart
@Composable
fun StatCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
                Text(title, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                content()
            }
        )
    }
}