package com.example.datadetective.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.datadetective.data.Task
import com.example.datadetective.ui.charts.ManipulatedChart

@Composable
fun InfoScreen( tasks: List<Task>, onBack: () -> Unit) {

    var currentIndex by remember { mutableStateOf(0) }
    var showCorrect by remember { mutableStateOf(false) }
    val task = tasks[currentIndex] // Aktuell angezeigtes Beispiel anhand des Index
    val total = tasks.size
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top) {

        //Titel + seitenzahl
        Text(
            text = "Historisches Beispiel (${currentIndex + 1}/$total)",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(task.title, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        Text(task.description, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        //Manipuliertes Diagramm
        ManipulatedChart(
            task = task,
            showCorrect = showCorrect
        )

        Spacer(modifier = Modifier.height(16.dp))
        //Explanation
        task.explanation?.let {
            Text("Erklärung", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(it)
        }

        Spacer(modifier = Modifier.height(24.dp))
        //Manpualtione auflösen/anzeigen button
        Button(
            onClick = { showCorrect = !showCorrect },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (showCorrect)
                    "Manipulation anzeigen"
                else
                    "Manipulation auflösen"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        //Nächstes Beispiel(falls mehrere Manipulationen)
        if (currentIndex < total - 1) {
            Button(
                onClick = {
                    currentIndex++
                    showCorrect = false
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Nächstes Beispiel")
            }
        } else {
            //Zurück Button
            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Zurück")
            }
        }
    }
}