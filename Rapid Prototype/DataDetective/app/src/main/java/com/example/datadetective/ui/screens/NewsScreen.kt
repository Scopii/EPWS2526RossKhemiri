package com.example.datadetective.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp

@Composable
fun NewsScreen(){
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,)
    {
        Spacer(modifier = Modifier.height(1.dp))
        Text("News", style = MaterialTheme.typography.headlineLarge)

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        ){
            Text(
                text = "Trumps Wahl, schlechter als erwartet?",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(1.dp))

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        ){
            Text(
                text = "Nvidia, AI Hype neues High?",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(1.dp))

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        ){
            Text(
                text = "Frauen wirklich schlechte Gamer?",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge)
        }
    }
}