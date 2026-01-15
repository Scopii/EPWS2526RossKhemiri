package com.example.datadetective.data

//Diagrammtypen+kompatible Manipulation
enum class ChartType {
    BAR { override val allowedManipulations =
        setOf(ManipulationType.TRUNCATED_VALUE_AXIS, ManipulationType.TRUNCATED_CATEGORY_AXIS) },

    HORIZONTAL_BAR { override val allowedManipulations =
        setOf(ManipulationType.TRUNCATED_VALUE_AXIS, ManipulationType.TRUNCATED_CATEGORY_AXIS) },

    LINE { override val allowedManipulations =
        setOf(ManipulationType.TRUNCATED_VALUE_AXIS) };

    abstract val allowedManipulations: Set<ManipulationType>
}