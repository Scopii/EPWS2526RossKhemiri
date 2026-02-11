package com.example.datadetective.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.datadetective.viewmodel.GameViewModel
import com.example.datadetective.viewmodel.ManipulationMode

@Composable
fun HomeScreen(
    viewModel: GameViewModel,
    onStartGame: (ManipulationMode) -> Unit,
    onNews: () -> Unit,
    onAchievements : () -> Unit,
    onUserScreen: () -> Unit,
) {
    val profile = viewModel.userProfile.collectAsState().value

    var showModeDialog by remember { mutableStateOf(false) }

    // Spielmodus auswahl über dialogfenster, anschließender aufgabenstart2w
    if (showModeDialog) {
        AlertDialog(
            onDismissRequest = { showModeDialog = false },
            title = { Text("Schwierigkeitsgrad wählen") },
            text = { Text("Mit wie vielen Manipulationen möchtest du spielen?") },
            confirmButton = {
                Column {
                    Button(
                        onClick = {
                            showModeDialog = false
                            onStartGame(ManipulationMode.SINGLE)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("1 Manipulation")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            showModeDialog = false
                            onStartGame(ManipulationMode.DOUBLE)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("2 Manipulationen")
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { showModeDialog = false }) {
                    Text("Abbrechen")
                }
            }
        )
    }
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
            Text(
                "${if (profile.titel != null) profile.titel + " " else ""}${profile.name}",
                style = MaterialTheme.typography.headlineLarge
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        // Game Anzeige
        Card {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Level: ${profile.level}")
                Text("XP: ${profile.xp} / ${profile.level * 100}")
                LinearProgressIndicator(
                    progress = (profile.xp % 100) / 100f,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(64.dp))

        Button(onClick = { showModeDialog = true }) { //Startenbutton aktiviert Spielmodiauswahlfenster
            Text("Starten")
        }

        Button(onClick = onNews) {
            Text("News")
        }

        Button(onClick = onUserScreen) {
            Text("Personal")
        }

        Button(onClick = { viewModel.resetProgress() }) {
            Text("Reset Stats")
        }
    }
}