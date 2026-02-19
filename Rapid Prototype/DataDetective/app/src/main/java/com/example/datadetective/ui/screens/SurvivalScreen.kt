package com.example.datadetective.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.datadetective.viewmodel.SurvivalResult

@Composable
fun SurvivalScreen(
    result: SurvivalResult,
    onBackToHome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Run beendet", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(24.dp))

        Card {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text("Aufgaben geschafft", style = MaterialTheme.typography.labelLarge)
                Text(result.streak.toString(), style = MaterialTheme.typography.headlineLarge)

                Spacer(Modifier.height(12.dp))

                Text("Bester Run", style = MaterialTheme.typography.labelLarge)
                Text(result.best.toString(), style = MaterialTheme.typography.titleLarge)

                Spacer(Modifier.height(12.dp))
            }
        }

        Spacer(Modifier.height(32.dp))

        Button(onClick = onBackToHome) {
            Text("Zurück zum Menü")
        }
    }
}
