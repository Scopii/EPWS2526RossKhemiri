package com.example.datadetective.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.datadetective.viewmodel.GameViewModel

@Composable
fun HomeScreen(
    viewModel: GameViewModel,
    onStartGame: () -> Unit,
    onNews: () -> Unit,
    onAchievements : () -> Unit,
) {
    // Wenn sich ViewModel ändert neues UI
    val userProfile = viewModel.userProfile

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text("Data Detective", style = MaterialTheme.typography.displayMedium)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(onClick = onAchievements) {
            Text("${if (userProfile.titel != null) userProfile.titel + " " else ""}${userProfile.name}", style = MaterialTheme.typography.headlineLarge)
        }
        Spacer(modifier = Modifier.height(32.dp))

        // Game Anzeige
        Card {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Level: ${userProfile.level}")
                Text("XP: ${userProfile.xp} / ${userProfile.level * 100}")
                LinearProgressIndicator(
                    progress = (userProfile.xp % 100) / 100f,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(64.dp))

        Button(onClick = onStartGame) {
            Text("Starten")
        }

        Button(onClick = onNews) {
            Text("News")
        }
    }
}