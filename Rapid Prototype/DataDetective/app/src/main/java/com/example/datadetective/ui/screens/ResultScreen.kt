package com.example.datadetective.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.datadetective.data.Task
import com.example.datadetective.ui.charts.ManipulatedChart
import com.example.datadetective.viewmodel.GameViewModel
import kotlinx.coroutines.delay

@Composable
fun ResultScreen(
    viewModel: GameViewModel,
    task: Task,
    selectedAnswerIndices: Set<Int>,
    onNext:()-> Unit,
    onInfo:()-> Unit
) {
    var showCorrect by remember { mutableStateOf(false) }
    val gainedXp = viewModel.lastGainedXp

    // XP Popup logic
    var showXpPopup by remember { mutableStateOf(true) }
    val targetAlpha = if (showXpPopup) 1f else 0f
    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = 300) // Fade duration
    )

    // Auto-hide nach 1 Sekunde
    LaunchedEffect(Unit) {
        delay(1000)
        showXpPopup = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Auswertung", style = MaterialTheme.typography.headlineMedium)
                Text(
                    "Run: ${viewModel.sessionQuestionCount}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            ManipulatedChart(task = task, showCorrect = showCorrect)
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {showCorrect = !showCorrect }, modifier = Modifier.fillMaxWidth())
            { Text(if(showCorrect)"Manipulation anzeigen" else "Manipulation auflösen") }
            Spacer(modifier = Modifier.height(8.dp))

            // Answer evaluation cards
            task.options.forEachIndexed{ index, text ->
                val isCorrect = index in task.correctOptionIndices
                val isSelected = index in selectedAnswerIndices
                val backgroundColor = when {
                    isCorrect && isSelected -> Color(0xCD66F13B)
                    isCorrect && !isSelected -> Color(0xCDFF8C00)
                    isSelected && !isCorrect -> Color(0xCDC62828)
                    else -> Color(0x00FFFFFF)
                }
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = backgroundColor)
                ){
                    Text(
                        text = text,
                        modifier = Modifier.padding(10.dp),
                        style = MaterialTheme.typography.bodyLarge)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = { onInfo() }, modifier = Modifier.fillMaxWidth()) {
                Text("Historisches Beispiel")
            }

            Button(onClick = { onNext() }, modifier = Modifier.fillMaxWidth()) {
                Text("Nächste Aufgabe")
            }
        }

        // XP Popup overlay
        if (gainedXp > 0 && alpha > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(alpha),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(
                    text = "+$gainedXp XP",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF66F13B) // Grün
                    ),
                    modifier = Modifier
                        .background(
                            Color.Black.copy(alpha = 0.5f),
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(24.dp)
                )
            }
        }


        //Pop up für erreichte daily challenges
        val dailyXp = viewModel.dailyXpPopup
        if (dailyXp != null) {
            AlertDialog(
                onDismissRequest = { viewModel.dailyXpPopup = null },
                title = { Text("Daily Challenge geschafft!") },
                text = {
                    Column {
                        Text(
                            dailyXp.hint,
                            style = MaterialTheme.typography.bodyLarge)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "+${dailyXp.xp} XP",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color(0xFF4CAF50))
                    }
                },
                confirmButton = {
                    TextButton(onClick = { viewModel.dailyXpPopup = null }) {
                        Text("Nice!")
                    }
                }
            )
        }
    }
}