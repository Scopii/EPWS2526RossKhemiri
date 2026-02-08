package com.example.datadetective.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.datadetective.viewmodel.GameViewModel

@Composable
fun UserScreen(viewModel: GameViewModel){
    // HIER MUSS NOCH DIE EIGENE STATISTIK DES SPIELERVERHALTENS HIN

    val profile = viewModel.userProfile.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize().padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text("Personal Stats", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(10.dp))

        Card() {
            Column(
                modifier = Modifier.padding(140.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) { Text("PERSONAL GRAPH HERE") }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row() {
            // Level
            Card() {
                Column(
                    modifier = Modifier.padding(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { Text("Level: ${profile.level}") }
            }
            // XP
            Card() {
                Column(
                    modifier = Modifier.padding(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { Text("XP: ${profile.xp}") }
            }
            // Correct Percentage
            Card() {
                Column(
                    modifier = Modifier.padding(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val percentage = (profile.solvedQuestions.toFloat() / profile.doneQuestions.toFloat()) * 100
                    Text("Correct: ${"%.2f".format(percentage)}%")
                }
            }

        }
        Spacer(modifier = Modifier.height(4.dp))

        Row() {
            // Highest Streak
            Card() {
                Column(
                    modifier = Modifier.padding(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { Text("Current Streak: ${profile.currentStreak}") }
            }
            Spacer(modifier = Modifier.height(4.dp))
            // Current Streak
            Card() {
                Column(
                    modifier = Modifier.padding(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { Text("Highest Streak: ${profile.highestStreak}") }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))

        Row() {
            // Solved Questions
            Card() {
                Column(
                    modifier = Modifier.padding(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { Text("Correct Questions: ${profile.solvedQuestions}") }
            }
            Spacer(modifier = Modifier.height(4.dp))

            // Done Questions
            Card() {
                Column(
                    modifier = Modifier.padding(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { Text("Total Questions: ${profile.doneQuestions}") }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
    }

}