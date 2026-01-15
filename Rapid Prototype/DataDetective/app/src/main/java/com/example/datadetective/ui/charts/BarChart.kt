package com.example.datadetective.ui.charts

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas


@Composable
fun BarChart(
    yData: List<Float>,      //y-werte der balken
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

        val paint = Paint().apply { //für textbeschrooiftungen
            color = android.graphics.Color.BLACK
            textSize = 24f
            textAlign = Paint.Align.CENTER
        }

        //y-achse Rasterlinen+Beschriftung
        val stepValue = (maxY - minY) / ySteps //abstand zwischen zwei Rasterlinien
        repeat(ySteps + 1) { i ->
            val value = minY + stepValue * i  //Wert für Achsenbeschrfitung
            val y = paddingTop + chartHeight - (i / ySteps.toFloat()) * chartHeight //postion für rasterlinie und beschriftung

            //horizontale Rasterlinie
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

        //anzahl sichtbarer Kategorien/Balken
        val visibleSpan = (xEnd - xStart + 1f).coerceAtLeast(1f)
        //Balkenplatz pro kategorie
        val stepWidth = chartWidth / visibleSpan
        //tatsächliche Balkenbreite
        val barWidth = stepWidth * 0.6f

        //Balken + Beschriftung
        yData.forEachIndexed { index, value ->
            val localIndex = index.toFloat() - xStart
            if (localIndex<0f|| localIndex > visibleSpan) return@forEachIndexed

            val xCenter = paddingLeft + stepWidth * (localIndex + 0.5f)
            val normalized = (value - minY) / (maxY - minY)
            val barHeight = normalized.coerceIn(0f, 1f) * chartHeight
            val yTop = paddingTop + chartHeight - barHeight

            // Balken
            drawRect(
                color = Color(0xFF2196F3),
                topLeft = Offset(xCenter - barWidth / 2, yTop),
                size = Size(barWidth, barHeight))

            // Zahlenwert über dem Balken
            drawContext.canvas.nativeCanvas.drawText(
                "${value.toInt()} $unit",
                xCenter,
                yTop - 8f,
                paint.apply {
                    textAlign = Paint.Align.CENTER
                })

            // X-achsenbeschriftung
                drawContext.canvas.nativeCanvas.drawText(
                    xData[index],
                    xCenter,
                    paddingTop + chartHeight + 30f,
                    paint)

        }
    }
}

