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
fun HorizontalBarChart(
    xData: List<Float>,      //x-werte der balken
    yData: List<String>,    //Beschriftungen der y-achse
    modifier: Modifier,
    minX: Float,            //untere Grenze x-achse
    maxX: Float,            //obere Grenze x-achse
    yStart: Float,          //startindex des sichtbaren Kategoriebereichs
    yEnd: Float,            //endindex des sichtbaren Kategoriebereichs
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

        val paint = Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = 24f
            textAlign = Paint.Align.CENTER
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
                "${value.toInt()} $unit",
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
            val barLength = normalized * chartWidth

            //Balken
            drawRect(
                color = Color(0xFF2196F3),
                topLeft = Offset(paddingLeft, yCenter - barHeight / 2),
                size = Size(barLength, barHeight))

            // Y-Achsenbeschriftung
            drawContext.canvas.nativeCanvas.drawText(
                yData[index],
                paddingLeft - 12f,
                yCenter + 8f,
                paint.apply { textAlign = Paint.Align.RIGHT })


        }
    }
}

