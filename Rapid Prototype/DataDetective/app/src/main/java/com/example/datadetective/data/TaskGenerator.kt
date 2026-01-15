package com.example.datadetective.data

import kotlin.random.Random

//Erzeugt aus einem neutralen Datensatz eine spielbare Aufgabe mit zufälliger Manipulation
object TaskGenerator {
    fun generate(base: ChartData): Task {

        //Zufälliger Diagrammtyp aus erlaubten Typen
        val chartType = base.supportedChartTypes.random()
        //Erlaubte Manipulationen für den Diagrammtyp
        val allowedManipulations = chartType.allowedManipulations
        //Zufällige Manipulation aus den erlaubten
        val manipulationType = allowedManipulations.random()

        val random = Random(base.id)

        //Erzeugt ein Manipulations-Objekt, das beschreibt, wie das Diagramm manipuliert wird
        val manipulation = Manipulation(type = manipulationType, //ManipulationsTyp
            intensity = ManipulationParameters.manipulationIntensity(manipulationType, random), //zufällige Stärke der Manipulation
            categoryRange = if (manipulationType == ManipulationType.TRUNCATED_CATEGORY_AXIS)
                ManipulationParameters.categoryRange(base.xData.size, random)//erzeugt bei Kategorie-Achse Manipulation einen zufälligen Bereich von Kategorien
            else null)

        //Richtige Antwort basierend auf Manipulationstyp
        val correctAnswer = AnswerPool.correctAnswerFor(manipulationType)
        //Erzeugt Antwortmöglichkeiten
        val (options, correctIndex) = generateAnswerOptions(correctAnswer, AnswerPool.allAnswers)

        return Task(
            id = base.id,
            title = base.title,
            chartType = chartType,
            description = base.description,
            unit = base.unit,
            yValues = base.yValues,
            xData = base.xData,
            options = options,
            correctOptionIndex = correctIndex,
            manipulation = manipulation
        )
    }

    //Erzeugt multiple-Choice-Antwoeren aus einem Antwortpool mit genau einer Richtig-Antwort
    fun generateAnswerOptions(correctAnswer: String, pool: List<String>, numberOfOptions: Int = 4): Pair<List<String>, Int>{

        val wrongAnswers = pool.filter { it != correctAnswer }
        val distractors = wrongAnswers.shuffled().take(numberOfOptions-1)
        val allOptions = (distractors + correctAnswer).shuffled()
        val correctIndex = allOptions.indexOf(correctAnswer)
        return Pair(allOptions,correctIndex)
    }
}

