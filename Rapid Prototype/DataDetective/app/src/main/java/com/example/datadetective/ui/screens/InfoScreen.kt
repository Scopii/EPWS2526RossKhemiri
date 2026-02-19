package com.example.datadetective.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import com.example.datadetective.data.Task
import com.example.datadetective.data.TaskGenerator
import com.example.datadetective.ui.charts.ManipulatedChart
import infoData

@Composable
fun InfoScreen(task: Task){
    val base = infoData.random();
    val sampleTask = TaskGenerator.generate(base);

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Bsp: ${task.manipulation.type} ...",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge)
        Text(sampleTask.title, style = MaterialTheme.typography.titleLarge) //Titel
        Spacer(modifier = Modifier.height(8.dp))
        Text(sampleTask.description, style = MaterialTheme.typography.bodyMedium) //Beschreibung
        Spacer(modifier = Modifier.height(16.dp))

        ManipulatedChart(task = sampleTask, showCorrect = true) // Manipuliertes Diagramm
        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(1.dp))

        Text("Beschreibung der Manipulation ...${base.explanation}")
    }
}