package com.example.datadetective.ui.charts

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.datadetective.data.ChartType
import com.example.datadetective.data.ManipulationType
import com.example.datadetective.data.Task


/*Entscheidet, ob Chart manipuliert oder korrekt dargesteltt wird(showCorrect),
berechneten werte werden anschließend an das jeweilige Basis-Diagramm übergebe*/
@Composable
fun ManipulatedChart(task: Task, showCorrect: Boolean, modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(260.dp))
{

    val ydata = task.yValues
    val xData = task.xData
    val unit = task.unit
    val manipulations = task.manipulations //liste aktiver manipulationen
    //Manipulationen nach Typ extrahieren(bei DOUBLE Mode können mehrere gleichzeitig existieren)
    val truncatedValue = manipulations.firstOrNull {
        it.type == ManipulationType.TRUNCATED_VALUE_AXIS
    }

    val truncatedCategory = manipulations.firstOrNull {
        it.type == ManipulationType.TRUNCATED_CATEGORY_AXIS
    }

    val distortion = manipulations.firstOrNull {
        it.type == ManipulationType.DISTORTED_BAR_LENGTH
    }

    val highlight = manipulations.firstOrNull {
        it.type == ManipulationType.COLOR_HIGHLIGHTING
    }
    //ColorHighlightlogik
    val highlightEnabled =
        !showCorrect && highlight != null

    val highlightedIndex =
        if (highlightEnabled)
            highlight.categoryRange?.first //Nimmt einen zufälligen kategorieindex
        else
            null
    //unmanipulierte y-Werte
    val rawMinY = 0f
    val baseMaxY = ydata.maxOrNull() ?: 1f
    val baselineMaxY = baseMaxY * 1.20f


    //Zielwerte für Werte-Achse
    val targetMinY =
        when {
            showCorrect -> rawMinY
            truncatedValue != null ->
                truncatedValue.fixedMinValue ?: truncatedValue.type.manipulateMinValue(ydata, truncatedValue)
            else -> rawMinY
        }
    val targetMaxY = truncatedValue?.fixedMaxValue ?: baselineMaxY

    //Startpunkt des sichbaren Kategoriebereich (wird nur verändert bei TRUNCATED_CATEGORY_AXIS Manipulation)
    val targetCategoryStart =
        when {
            showCorrect -> 0f
            truncatedCategory != null ->
                truncatedCategory.categoryRange?.first?.toFloat() ?: 0f
            else -> 0f
        }
    //Endpunkt des sichbaren Kategoriebereich (wird nur verändert bei TRUNCATED_CATEGORY_AXIS Manipulation)
    val targetCategoryEnd =
        when {
            showCorrect -> (xData.size - 1).toFloat()
            truncatedCategory != null ->
                truncatedCategory.categoryRange?.last?.toFloat()
                    ?: (xData.size - 1).toFloat()
            else -> (xData.size - 1).toFloat()
        }
    //Balkenverzerrung
    val targetDistortion =
        if (!showCorrect && distortion != null)
            distortion.intensity
        else
            1f
    //Animationen für showCorrect wechsel
    val animatedMinY by animateFloatAsState(
        targetValue = targetMinY,
        animationSpec = tween(1200, easing = FastOutSlowInEasing))

    val animatedMaxY by animateFloatAsState(
        targetValue = targetMaxY,
        animationSpec = tween(1200, easing = FastOutSlowInEasing))

    val animatedCategoryStart by animateFloatAsState(
        targetValue = targetCategoryStart,
        animationSpec = tween(1200, easing = FastOutSlowInEasing))

    val animatedCategoryEnd by animateFloatAsState(
        targetValue = targetCategoryEnd,
        animationSpec = tween(1200, easing = FastOutSlowInEasing))

    val animatedDistortion by animateFloatAsState(
        targetValue = targetDistortion,
        animationSpec = tween(1200, easing = FastOutSlowInEasing))

    //Rendering je nach ChartTyp
    when (task.chartType) {
        ChartType.BAR -> {
            BarChart(
                yData = ydata,
                xData = xData,
                modifier = modifier,
                minY = animatedMinY,
                maxY = animatedMaxY,
                xStart = animatedCategoryStart,
                xEnd = animatedCategoryEnd,
                distortionFactor = animatedDistortion,
                highlightedIndex = highlightedIndex,
                highlightEnabled = highlightEnabled,
                unit = unit
            )
        }
        ChartType.LINE -> {
            LineChart(
                yData = ydata,
                xData = xData,
                modifier = modifier,
                minY = animatedMinY,
                maxY = animatedMaxY,
                xStart = animatedCategoryStart,
                xEnd = animatedCategoryEnd,
                highlightedIndex = highlightedIndex,
                highlightEnabled = highlightEnabled,
                unit = unit
            )
        }
        ChartType.HORIZONTAL_BAR -> {
            HorizontalBarChart(
                xData = ydata,
                yData = xData,
                modifier = modifier,
                minX = animatedMinY, //
                maxX = animatedMaxY,
                yStart = animatedCategoryStart,
                yEnd = animatedCategoryEnd,
                distortionFactor = animatedDistortion,
                highlightedIndex = highlightedIndex,
                highlightEnabled = highlightEnabled,
                unit = unit
            )
        }
    }
}
