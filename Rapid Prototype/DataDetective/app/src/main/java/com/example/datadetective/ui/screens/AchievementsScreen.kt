package com.example.datadetective.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.datadetective.data.Condition
import com.example.datadetective.data.achievements
import com.example.datadetective.ui.screens.ComposeAchievement
import com.example.datadetective.viewmodel.GameViewModel

@Composable
fun AchievementsScreen(
    viewModel: GameViewModel,
    onSetAchievement: (String?) -> Unit
) {
    val profile = viewModel.userProfile.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text("Achievements", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(4.dp))

        var unlocked = 0

        // Loop für unlocked count
        achievements.forEach { achievement ->
            val requiredValue = achievement.required

            val curValue = when (achievement.condition) {
                Condition.HIGHEST_STREAK -> profile.highestStreak
                Condition.QUESTIONS_SOLVED -> profile.solvedQuestions
                Condition.QUESTIONS_DONE -> profile.doneQuestions
                Condition.LEVEL_REACHED -> profile.level
                Condition.LOWEST_QUESTION_TIME -> 0
            }
            if (curValue >= requiredValue) unlocked += 1
        }

        Text("(${unlocked}/${achievements.size})")
        Spacer(modifier = Modifier.height(32.dp))

        // Loop für Buttons
        achievements.forEach { achievement ->

            val requiredValue = achievement.required

            val curValue = when (achievement.condition) {
                Condition.HIGHEST_STREAK -> profile.highestStreak
                Condition.QUESTIONS_SOLVED -> profile.solvedQuestions
                Condition.QUESTIONS_DONE -> profile.doneQuestions
                Condition.LEVEL_REACHED -> profile.level
                Condition.LOWEST_QUESTION_TIME -> 0
            }

            var unlocked = 0

            if (curValue >= requiredValue) {
                unlocked += 1

                Button(
                    onClick = { onSetAchievement(achievement.title) },
                    enabled = true,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ComposeAchievement(
                        title = achievement.title,
                        currentString = curValue.toString(),
                        requiredString = requiredValue.toString(),
                        hint = achievement.hint,
                        showAll = false
                    )
                }
            } else {
                OutlinedButton(
                    onClick = { onSetAchievement(achievement.title) },
                    enabled = false,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ComposeAchievement(
                        title = "?",
                        currentString = curValue.toString(),
                        requiredString = requiredValue.toString(),
                        hint = achievement.hint,
                        showAll = true
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun ComposeAchievement(title: String, currentString: String, requiredString: String, hint: String, showAll: Boolean) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth()) {
            if (showAll) Text(
                currentString,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )
            Text(
                title,
                modifier = Modifier.weight(1f),
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
            if(showAll) Text(
                requiredString,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
        Text(hint, textAlign = TextAlign.Center)
    }
}