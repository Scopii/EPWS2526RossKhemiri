package com.example.datadetective.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import com.example.datadetective.data.Task
import com.example.datadetective.ui.charts.ManipulatedChart

@Composable
fun ResultScreen(task: Task, selectedAnswerIndex: Int, onNext:()-> Unit, onInfo:()-> Unit){

    var showCorrect by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text(text="Auswertung",style= MaterialTheme.typography.headlineMedium) //Überschrift
        Spacer(modifier = Modifier.height(16.dp))

        ManipulatedChart(task = task, showCorrect = showCorrect) //Diagramm (umschaltbar)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {showCorrect = !showCorrect }, modifier = Modifier.fillMaxWidth()) //Button zur Tranformation des Diagramm
        {Text(if(showCorrect)"Manipulation anzeigen" else "Manipulation auflösen") }
        Spacer(modifier = Modifier.height(8.dp))

        //Antwortauswertung
        task.options.forEachIndexed{ index, text ->
            val isCorrect = index == task.correctOptionIndex
            val isSelected = index == selectedAnswerIndex
            val backgroundColor = when {
                isCorrect-> Color(0xCD66F13B) //Richtige Antwort: grün
                isSelected && !isCorrect -> Color(0xEBBE1F29) //falsche gewählte antwort: rot
                else -> Color(0x00FFFFFF)
            }
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = backgroundColor)
            ){
                Text(
                    text = text,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge)
            }
    }
    Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                onInfo()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Historisches Beispiel")
        }
    Button(
        onClick = onNext,
        modifier = Modifier.fillMaxWidth()
    )   {
        Text("Nächste Aufgabe")
        }
    }
}