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
    val manipulation = task.manipulation

    //unmanipulierte y-Werte
    val rawMinY = 0f
    val rawMaxY = ydata.maxOrNull() ?: 1f

    //Zielwerte für Werte-Achse
    val targetMinY = if (showCorrect)
                        rawMinY
                     else
                         manipulation.type.manipulateMinValue(ydata, manipulation)

    val targetMaxY = if (showCorrect)
                        rawMaxY
                     else
                        manipulation.type.manipulateMaxValue(ydata, manipulation)

    //Gesamtanzahl der Kategorien
    val totalCount = xData.size

    //Startpunkt des sichbaren Kategoriebereich (wird nur verändert bei TRUNCATED_CATEGORY_AXIS Manipulation)
    val targetCategoryStart = if (showCorrect || manipulation?.categoryRange == null)
                                { 0f }
                              else {
                                manipulation.categoryRange.first.toFloat()
                            }
    //Endpunkt des sichbaren Kategoriebereich (wird nur verändert bei TRUNCATED_CATEGORY_AXIS Manipulation)
    val targetCategoryEnd = if (showCorrect || manipulation?.categoryRange == null)
                                { (totalCount - 1).toFloat() }
                            else {
                                manipulation.categoryRange.last.toFloat()
        }

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
                unit = unit
            )
        }
    }
}
