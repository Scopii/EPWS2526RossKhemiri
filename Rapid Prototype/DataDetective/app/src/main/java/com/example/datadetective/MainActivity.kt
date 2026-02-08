package com.example.datadetective

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.datadetective.data.UserData
import com.example.datadetective.ui.screens.AchievementsScreen
import com.example.datadetective.ui.screens.GameScreen
import com.example.datadetective.ui.screens.HomeScreen
import com.example.datadetective.ui.screens.InfoScreen
import com.example.datadetective.ui.screens.NewsScreen
import com.example.datadetective.ui.screens.ResultScreen
import com.example.datadetective.ui.theme.DataDetectiveTheme
import com.example.datadetective.viewmodel.GameViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.datadetective.ui.screens.UserScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataDetectiveTheme {
                val navController = rememberNavController()

                val data = remember { UserData(applicationContext) }

                val viewModel: GameViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            @Suppress("UNCHECKED_CAST")
                            return GameViewModel(data) as T
                        }
                    }
                )

                NavHost(navController = navController, startDestination = "home") {

                    composable("home") {
                        HomeScreen(
                            viewModel = viewModel,
                            onStartGame = { navController.navigate("game") },
                            onNews = {navController.navigate("news")},
                            onAchievements = {navController.navigate("achievements")},
                            onUserScreen = {navController.navigate("personal")},
                        )
                    }

                    composable("game") {
                        GameScreen(
                            viewModel = viewModel,
                            onAnswerLocked = {navController.navigate("result")}
                        )
                    }

                    composable("achievements") {
                        AchievementsScreen(
                            viewModel = viewModel,
                            onSetAchievement = { titel ->
                                viewModel.setTitel(titel)
                                navController.navigateUp()
                            }
                        )
                    }

                    composable("news") {
                        NewsScreen()
                    }

                    composable("personal") {
                        UserScreen(viewModel)
                    }

                    composable("info") {
                        val question = viewModel.currentTask
                        if (question != null)
                        InfoScreen(question)
                    }

                    composable("result") {
                        val question = viewModel.currentTask
                        val selectedIndex = viewModel.selectedAnswerIndex

                        if (question != null && selectedIndex != null) {
                            ResultScreen(
                                task = question,
                                selectedAnswerIndex = selectedIndex,
                                onNext = {
                                    viewModel.nextQuestion()
                                    navController.navigate("game") {
                                        popUpTo("game") { inclusive = true }
                                    }
                                },
                                onInfo = {navController.navigate("info")},
                            )
                        }
                    }
        }
            }}}}