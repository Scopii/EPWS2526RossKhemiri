package com.example.datadetective.ui.charts

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

import android.graphics.Paint
import androidx.compose.ui.graphics.nativeCanvas

@Composable
fun LineChart(
    yData: List<Float>,      //y-werte der Punkte
    xData: List<String>,    //Beschriftungen der x-achse
    modifier: Modifier,
    minY: Float,            //untere Grenze y-achse
    maxY: Float,            //obere Grenze y-achse
    xStart: Float,          //startindex des sichtbaren Kategoriebereichs
    xEnd: Float,            //endindex des sichtbaren Kategoriebereichs
    ySteps: Int = 4,
    unit: String = "") {

    Canvas(modifier = modifier) {

        // Layout
        val paddingLeft = 80f
        val paddingBottom = 60f
        val paddingTop = 20f
        val paddingRight = 20f

        val chartWidth = size.width - paddingLeft - paddingRight
        val chartHeight = size.height - paddingTop - paddingBottom

        val paint = Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = 24f
            textAlign = Paint.Align.CENTER
        }

        //y-achse Rasterlinen+Beschriftung
        val stepValue = (maxY - minY) / ySteps //abstand zwischen zwei Rasterlinien
        repeat(ySteps + 1) { i ->
            val value = minY + stepValue * i ////Wert für Achsenbeschrfitung
            val y = paddingTop + chartHeight - (i / ySteps.toFloat()) * chartHeight //postion für rasterlinie und beschriftung

            //horizontaleRasterlinie
            drawLine(
                color = Color.LightGray,
                start = Offset(paddingLeft, y),
                end = Offset(size.width - paddingRight, y),
                strokeWidth = 1f)

            //y-achsenbeschriftung
            drawContext.canvas.nativeCanvas.drawText(
                "${value.toInt()} $unit",
                paddingLeft - 10f,
                y + 8f,
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

        //Platz zwischen zwei Datenpunkten
        val stepWidth = chartWidth / (yData.size -1).coerceAtLeast(1)


        //berechnet horizontale postion für y-Datenpunkt
        fun xForIndex(index: Int): Float {
            val localIndex = index.toFloat() - xStart
            return paddingLeft + stepWidth * localIndex
        }
        //berechnet vertikale postion für y-Datenpunkt
        fun yForValue(value: Float): Float {
            val normalized = (value - minY) / (maxY - minY)
            return paddingTop + chartHeight - normalized.coerceIn(0f, 1f) * chartHeight
        }

        //Chart-Linie
        for (i in 0 until yData.lastIndex) {
            drawLine(
                color = Color(0xFF2196F3),
                start = Offset(xForIndex(i), yForValue(yData[i])),
                end = Offset(xForIndex(i + 1), yForValue(yData[i + 1])),
                strokeWidth = 4f)
        }

        //Punkte + X-Beschriftung
        yData.forEachIndexed { index, value ->
            val x = xForIndex(index)
            val y = yForValue(value)
            if (index.toFloat() < xStart || index.toFloat() > xEnd) return@forEachIndexed
            // Punkt
            drawCircle(
                color = Color(0xFF1976D2),
                radius = 6f,
                center = Offset(x, y))

            // X-achsenbeschriftun
            if (index < xData.size) {
                drawContext.canvas.nativeCanvas.drawText(
                    xData[index],
                    x,
                    paddingTop + chartHeight + 30f,
                    paint)
            }
        }
    }
}
