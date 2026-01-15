package com.example.datadetective.data

//Definiert die verschiedenen Arten von Diagrammmanipulationen
enum class ManipulationType {

    TRUNCATED_VALUE_AXIS { //Verkürzte Werte-Achse (Y bei Bar/Line, X bei HorizontalBar) damit Achse nicht bei 0 beginnt

        override fun manipulateMinValue(data: List<Float>, manipulation: Manipulation): Float {
            val min = data.minOrNull() ?: 0f
            return min * manipulation.intensity } //Minimalwert wird auf einen Wert über 0 gesetzt
    },

    TRUNCATED_CATEGORY_AXIS { //Verkürzte Kategorie-Achse(X bei Bar, Y bei HorizontalBar)

        override fun categoryRange(size: Int, manipulation: Manipulation): IntRange? =
            manipulation.categoryRange //berechnet eingegrenzten Kategorie Bereich
    },
    /*
    PARTIAL_AXIS
    UNEVEN_INTERVAL,
    WARPED_SCALING,
    MISSING_INFO,
    STYLE,
    OVERLAP,
    MEDIAN_SWAP,
    CHERRY_PICKING,
    OPTIMAL_TIMESPAN
    */
    NONE;
    open fun manipulateMinValue(data: List<Float>, manipulation: Manipulation): Float = 0f
    open fun manipulateMaxValue(data: List<Float>, manipulation: Manipulation): Float = data.maxOrNull() ?: 1f
    open fun categoryRange(size: Int, manipulation: Manipulation): IntRange? = null
}

