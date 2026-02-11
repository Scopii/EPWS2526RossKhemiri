package com.example.datadetective.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.datadetective.ui.charts.MasteryRadarChart
import com.example.datadetective.ui.charts.masteryByManipulation
import com.example.datadetective.viewmodel.GameViewModel

@Composable
fun UserScreen(viewModel: GameViewModel){
    val profile = viewModel.userProfile.collectAsState().value
    //Berechnet Erfolgsquoten pro Manipulationstyp
    val masteryByType = remember(profile.solvedByManipulation, profile.failedByManipulation) {
        masteryByManipulation(profile.solvedByManipulation, profile.failedByManipulation)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text("Personal Stats", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(10.dp))
        //MasteryChart
        StatCard(title = "Manipulation Mastery") {
            MasteryRadarChart(mastery = masteryByType)
        }
        Spacer(modifier = Modifier.height(10.dp))
        StatRow {
            // Level
            StatValue("Level", profile.level)
            // XP
            StatValue("XP", profile.xp)
            // Correct Percentage
            val percentage = (profile.solvedQuestions.toFloat() / profile.doneQuestions.toFloat()) * 100f
            StatValue("Correct","%.2f%%".format(percentage))
            }

        Spacer(modifier = Modifier.height(6.dp))


        StatRow {
            // Current Streak
            StatValue("Current Streak", profile.currentStreak)
            // Highest Streak
            StatValue("Highest Streak", profile.highestStreak)
    }
        Spacer(modifier = Modifier.height(6.dp))


        StatRow {
            // Solved Questions
            StatValue("Correct Questions", profile.solvedQuestions)
            // Done Questions
            StatValue("Total Questions", profile.doneQuestions)
        }
    }
        Spacer(modifier = Modifier.height(6.dp))
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
//Card für Werte
@Composable
fun StatValue(label: String, value: Any) {
    Card {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(label, style = MaterialTheme.typography.bodySmall)
            Text(value.toString(), style = MaterialTheme.typography.bodyMedium)
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