package com.example.datadetective.ui.charts

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import com.example.datadetective.data.ManipulationType
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun MasteryRadarChart(
    mastery: Map<ManipulationType, Float>,
    comparisonMastery: Map<ManipulationType, Float>? = null,
    modifier: Modifier = Modifier
        .size(320.dp)
) {
    val types = mastery.keys.toList() //alle manipulationstypen
    if (types.isEmpty()) return

    Canvas(modifier = modifier) {

        val center = center
        val radius = size.minDimension * 0.35f
        val levels = 4
        val angleStep = (2 * Math.PI / types.size).toFloat() //Winkelabstand zwischen zwei Manipulationstypen

        val labelPaint = Paint().apply {
            color = android.graphics.Color.DKGRAY
            textSize = 26f
            textAlign = Paint.Align.CENTER
        }

        //Hintergrundgitter

        repeat(levels) { level ->
            val r = radius * (level + 1) / levels
            val path = Path()

            types.forEachIndexed { index, _ ->
                val angle = angleStep * index - Math.PI.toFloat() / 2
                val x = center.x + r * cos(angle)
                val y = center.y + r * sin(angle)
                if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
            }
            path.close()

            drawPath(
                path = path,
                color = Color.LightGray.copy(alpha = 0.45f),
                style = Stroke(width = 1.2f)
            )
        }

        //Achsen

        types.forEachIndexed { index, type ->
            val angle = angleStep * index - Math.PI.toFloat() / 2
            val x = center.x + radius * cos(angle)
            val y = center.y + radius * sin(angle)

            drawLine(
                color = Color.LightGray,
                start = center,
                end = Offset(x, y),
                strokeWidth = 1.5f
            )

            drawContext.canvas.nativeCanvas.drawText(
                type.label,
                center.x + (radius + 24f) * cos(angle),
                center.y + (radius + 24f) * sin(angle),
                labelPaint
            )
        }

        // Mastery linie

        val masteryPath = Path()

        types.forEachIndexed { index, type ->
            val value = mastery[type]?.coerceIn(0f, 1f) ?: 0f
            val angle = angleStep * index - Math.PI.toFloat() / 2
            val r = radius * value
            val x = center.x + r * cos(angle)
            val y = center.y + r * sin(angle)

            if (index == 0) masteryPath.moveTo(x, y)
            else masteryPath.lineTo(x, y)
        }
        masteryPath.close()

        //Fläche
        drawPath(
            path = masteryPath,
            color = Color(0xFF455A64).copy(alpha = 0.28f)
        )

        //Kontur
        drawPath(
            path = masteryPath,
            color = Color(0xFF455A64),
            style = Stroke(width = 4f)
        )

        if (comparisonMastery != null) {
            val compPath = Path()
            types.forEachIndexed { index, type ->
                val value = comparisonMastery[type]?.coerceIn(0f, 1f) ?: 0f
                val angle = angleStep * index - Math.PI.toFloat() / 2
                val x = center.x + radius * value * cos(angle)
                val y = center.y + radius * value * sin(angle)
                if (index == 0) compPath.moveTo(x, y) else compPath.lineTo(x, y)
            }
            compPath.close()
            drawPath(compPath, color = Color(0xFFFF9800).copy(alpha = 0.15f))
            drawPath(compPath, color = Color(0xFFFF9800), style = Stroke(width = 2f))
        }
    }
}
//Berechnet Erfolgsquote pro Manipulation
fun masteryByManipulation(
    solved: Map<ManipulationType, Int>,
    failed: Map<ManipulationType, Int>
): Map<ManipulationType, Float> {

    return ManipulationType.entries.associateWith { type ->
        val s = solved[type] ?: 0
        val f = failed[type] ?: 0
        val total = s + f

        if (total == 0) 0f else s.toFloat() / total
    }
}
