package com.example.datadetective.ui.charts

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import kotlin.math.pow

@Composable
fun HorizontalBarChart(
    xData: List<Float>,      //x-werte der balken
    yData: List<String>,    //Beschriftungen der y-achse
    modifier: Modifier,
    minX: Float,            //untere Grenze x-achse
    maxX: Float,            //obere Grenze x-achse
    yStart: Float,          //startindex des sichtbaren Kategoriebereichs
    yEnd: Float,            //endindex des sichtbaren Kategoriebereichs
    distortionFactor: Float = 1f, // Verzerrung der Balkenhöhe
    highlightedIndex: Int? = null, // Welche Kategorie ist hervorgehoben
    highlightEnabled: Boolean = false,
    xSteps: Int = 5,
    unit: String = "") {
    Canvas(modifier = modifier) {

        // Layout
        val paddingLeft = 80f
        val paddingBottom = 60f
        val paddingTop = 20f
        val paddingRight = 20f

        val chartWidth = size.width - paddingLeft - paddingRight
        val chartHeight = size.height - paddingTop - paddingBottom
        val baseColor = Color(0xFF3F51B5)
        val paint = Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = 24f
            textAlign = Paint.Align.CENTER
        }
        val valuePaint = Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = 24f
            textAlign = Paint.Align.LEFT
        }
        //x-achse Rasterlinen+Beschriftung
        val stepValue = (maxX - minX) / xSteps
        repeat(xSteps + 1) { i ->
            val value = minX + stepValue * i
            val x = paddingLeft + (i / xSteps.toFloat()) * chartWidth

            //vertikale Rasterlinie
            drawLine(
                color = Color.LightGray,
                start = Offset(x, paddingTop),
                end = Offset(x, paddingTop+chartHeight),
                strokeWidth = 1f)

            //x-achsenbeschriftung
            drawContext.canvas.nativeCanvas.drawText(
                "${formatAxisValue(value, 1)} $unit",
                x,
                paddingTop + chartHeight + 30f,
                paint.apply { textAlign = Paint.Align.RIGHT })
        }

        //y-achse
        drawLine(
            color = Color.Black,
            start = Offset(paddingLeft, paddingTop),
            end = Offset(paddingLeft, paddingTop + chartHeight),
            strokeWidth = 3f)
        //x-achse
        drawLine(
            color = Color.Black,
            start = Offset(paddingLeft, paddingTop + chartHeight),
            end = Offset(size.width - paddingRight, paddingTop + chartHeight),
            strokeWidth = 3f)

        //anzahl sichtbarer Kategorien/Balken
        val visibleSpan = (yEnd - yStart + 1f).coerceAtLeast(1f)
        //Balkenplatz pro kategorie
        val rowHeight = chartHeight / visibleSpan
        //tatsächliche Balkenbreite
        val barHeight = rowHeight * 0.6f

        //Balken + Beschriftung
        xData.forEachIndexed { index, value ->

            val localIndex = index.toFloat() - yStart
            if (localIndex < 0f || localIndex >= visibleSpan) return@forEachIndexed

            val yCenter = paddingTop + rowHeight * (localIndex + 0.5f)
            val normalized = ((value - minX) / (maxX - minX)).coerceIn(0f, 1f)

            //Verzerrung der Balkenhöhe bei DISTORTED_BAR_LENGTH
            val relativeLength = normalized.pow(distortionFactor)
            val barLength = relativeLength * chartWidth

            // Farb-Hervorhebung bei COLOR_HIGHLIGHTING
            val barColor = highlightColor(
                baseColor = baseColor,
                index = index,
                highlightedIndex = highlightedIndex,
                highlightEnabled = highlightEnabled
            )

            //Balken
            drawRect(
                color = barColor,
                topLeft = Offset(paddingLeft, yCenter - barHeight / 2),
                size = Size(barLength, barHeight))

            //Balkenbeschriftung (je nach balkenlänge beschriftung innerhalb oder außerhalbs des balkens)
            val valueText ="${formatAxisValue(value, 1)} $unit"
            val textWidth = valuePaint.measureText(valueText)
            val textOutside = paddingLeft + barLength + 8f
            val textInside = paddingLeft + barLength - textWidth - 8f
            val drawInside = textOutside + textWidth > size.width - paddingRight
            val textX = if (drawInside) textInside else textOutside
            drawContext.canvas.nativeCanvas.drawText(
                valueText,
                textX,
                yCenter + 8f,
                valuePaint
            )
            // Y-Achsenbeschriftung
            drawContext.canvas.nativeCanvas.drawText(
                yData[index],
                paddingLeft - 12f,
                yCenter + 8f,
                paint.apply { textAlign = Paint.Align.RIGHT })


        }
    }
}

