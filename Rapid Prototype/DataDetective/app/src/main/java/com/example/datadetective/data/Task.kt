package com.example.datadetective.data


//Klasse für eine konkrete Aufgabe
data class Task(
    val id: Int,
    val title: String,
    val chartType: ChartType,
    val description: String,
    val unit: String,
    val manipulation: Manipulation,
    val yValues: List<Float>,
    val xData: List<String>,
    val options: List<String>,
    val correctOptionIndex: Int, )