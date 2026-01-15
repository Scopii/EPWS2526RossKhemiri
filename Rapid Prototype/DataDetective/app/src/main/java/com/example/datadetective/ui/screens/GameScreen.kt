package com.example.datadetective.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.datadetective.ui.charts.ManipulatedChart
import com.example.datadetective.viewmodel.GameViewModel

@Composable
fun GameScreen(viewModel: GameViewModel, onAnswerLocked:()-> Unit) {
    val question = viewModel.currentTask //aktuelle Aufgabe
    val selectedIndex = viewModel.selectedAnswerIndex //ausgewählte antwort

    question?.let { q ->
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(q.title, style = MaterialTheme.typography.titleLarge) //Titel
            Spacer(modifier = Modifier.height(8.dp))
            Text(q.description, style = MaterialTheme.typography.bodyMedium) //Beschreibung
            Spacer(modifier = Modifier.height(16.dp))

            ManipulatedChart(task = q, showCorrect = false) // Manipuliertes Diagramm
            Spacer(modifier = Modifier.height(16.dp))

            // Antworten + Highlighting
            q.options.forEachIndexed { index, text ->
                val isSelected = selectedIndex == index

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { viewModel.selectAnswer(index) },
                    colors = CardDefaults.cardColors(containerColor = if (isSelected)
                                                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                                                      else
                                                                          MaterialTheme.colorScheme.surface),
                                                     border = if (isSelected)
                                                                BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                                                              else null
                ) {
                    Text(
                        text = text,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            //Antwort lock in
            Button(
                onClick = {
                    viewModel.submitAnswer()
                    onAnswerLocked()
                },
                enabled = selectedIndex != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Antwort bestätigen")
            }
        }
    }
}