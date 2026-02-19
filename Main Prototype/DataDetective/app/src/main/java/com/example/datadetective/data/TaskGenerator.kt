package com.example.datadetective.data


import com.example.datadetective.viewmodel.ManipulationMode
import kotlin.random.Random

//Erzeugt aus einem neutralen Datensatz eine spielbare Aufgabe mit zufälliger Manipulation
object TaskGenerator {
    fun generate(base: ChartData, mode: ManipulationMode): Task {

        //Zufälliger Diagrammtyp aus erlaubten Typen
        val chartType = base.supportedChartTypes.random()
        //Erlaubte Manipulationen für den Diagrammtyp
        val allowedManipulations = chartType.allowedManipulations
        val random = Random(base.id)
        // Wieviele Manipulationen
        val manipulationCount =
            when (mode) {
                ManipulationMode.SINGLE -> 1
                ManipulationMode.DOUBLE -> 2.coerceAtMost(allowedManipulations.size)
                ManipulationMode.SURVIVAL -> 0
            }


        //Zufällige Auswahl ein oder mehrerer Manipulationstypen
        val selectedTypes = allowedManipulations.shuffled().take(manipulationCount)


        //Erzeugt eine Manipulations-Liste, die Manipulationsobjekt(e) enthält, welche beschreiben, wie das Diagramm manipuliert wird

        val manipulations = selectedTypes.map { type ->
            when (type) {
                ManipulationType.TRUNCATED_CATEGORY_AXIS -> {
                    Manipulation(
                        type = type,
                        intensity = ManipulationParameters.manipulationIntensity(type, random),
                        categoryRange = ManipulationParameters.categoryRange(base.xData.size, random))//erzeugt einen zufälligen Bereich von Kategorien
                }
                ManipulationType.COLOR_HIGHLIGHTING -> {
                    val index =  Random.nextInt(base.xData.size) //zufälliger Index für highlighting
                    Manipulation(
                        type = type,
                        intensity = random.nextFloat() * 0.4f + 0.4f,
                        categoryRange = index..index)
                }
                else -> {
                    Manipulation(
                        type = type,
                        intensity = ManipulationParameters.manipulationIntensity(type, random),
                        categoryRange = null)
                }
            }
        }


        //Richtige Antwort(en) basierend auf Manipulationstyp(en)
        val correctAnswers = AnswerPool.correctAnswersFor(manipulations.map { it.type })
        //Erzeugt Antwortmöglichkeiten
        val (options, correctIndices) = generateAnswerOptions(correctAnswers, AnswerPool.allAnswers)

        return Task(
            id = base.id,
            title = base.title,
            chartType = chartType,
            description = base.description,
            unit = base.unit,
            yValues = base.yValues,
            xData = base.xData,
            options = options,
            correctOptionIndices = correctIndices,
            manipulations = manipulations
        )
    }

    //Erzeugt multiple-Choice-Antworten aus einem Antwortpool mit einer oder mehrern Richtig-Antworten je nach manipulationstypenmenge
    fun generateAnswerOptions(correctAnswers: List<String>, pool: List<String>, numberOfOptions: Int = 5): Pair<List<String>, Set<Int>> {
        val wrongAnswers = pool.filter { it !in correctAnswers }
        val distractors = wrongAnswers.shuffled().take(numberOfOptions - correctAnswers.size)
        val allOptions = (correctAnswers + distractors).shuffled()
        val correctIndices =
            allOptions.mapIndexedNotNull { index, text ->
                if (text in correctAnswers) index else null }.toSet()

        return Pair(allOptions, correctIndices)
    }
}