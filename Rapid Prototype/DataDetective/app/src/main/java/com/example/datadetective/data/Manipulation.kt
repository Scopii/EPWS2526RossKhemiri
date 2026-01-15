package com.example.datadetective.data
import androidx.compose.remote.creation.random
import kotlin.random.Random

//Beschreibt eine konrete Manipulation, die auf ein Diagramm angewendet wird
data class Manipulation (val type: ManipulationType, val intensity:Float,val categoryRange: IntRange? = null)



//Hilfsobjekt zur Erzeugung zufälliger aber sinnvoll begrenzter Manipulationsparameter
object ManipulationParameters {

    //Manipulationsintensität für z.B. keine Nullbaseline
    fun manipulationIntensity(type: ManipulationType, random: Random): Float =
        when (type) {
            ManipulationType.TRUNCATED_VALUE_AXIS -> random.nextFloat() * 0.13f + 0.80f // Achse beginnt bei 80-98% des Minimalwerts
            ManipulationType.TRUNCATED_CATEGORY_AXIS -> 1f
            ManipulationType.NONE -> 1f
        }

    //zufälliger Kategoriebereich(=weniger Balken) für verkürtze Kategorieachse bzw. Cherry Picking???
    fun categoryRange(size: Int, random: Random): IntRange {

        //Wert für Sichtbaren Ausschnitt für 40–70 % der Kategorien
        val windowSize = (size * (random.nextFloat() * 0.3f + 0.4f)).toInt().coerceAtLeast(2)

        // Zufälliger Startpunkt
        val start = random.nextInt(0, size - windowSize + 1)

        return start until (start + windowSize)
    }
}
