package com.example.datadetective.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import com.example.datadetective.data.Task
import com.example.datadetective.data.TaskGenerator
import com.example.datadetective.data.UserProfile
import com.example.datadetective.ui.charts.ManipulatedChart
import infoData

@Composable
fun AchievementsScreen(userProfile: UserProfile, onSetAchievement: () -> Unit){
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text("Achievements", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            userProfile.titel = "Magier"
            onSetAchievement()
        }) {
            Text("Magier")
        }

        Button(onClick = {
            userProfile.titel = "Detektiv"
            onSetAchievement()
        }) {
            Text("Detektiv")
        }

        Button(onClick = {
            userProfile.titel = "Bibliothekar"
            onSetAchievement()
        }) {
            Text("Bibliothekar")
        }
    }
}