package com.lession.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.lession.app.ui.theme.Purple

@Composable
fun LineGraph() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        val points = listOf(
            .48f,
            .62f,
            .55f,
            .72f,
            .66f,
            .82f,
            .75f,
            .88f
        )

        val path = Path()

        points.forEachIndexed { index, value ->
            val x = size.width * index / (points.size - 1)
            val y = size.height * (1f - value)

            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }

        drawPath(
            path = path,
            color = Purple,
            style = Stroke(
                width = 6f,
                cap = StrokeCap.Round
            )
        )

        points.forEachIndexed { index, value ->
            val x = size.width * index / (points.size - 1)
            val y = size.height * (1f - value)

            drawCircle(
                color = Purple,
                radius = 7f,
                center = Offset(x, y)
            )
        }
    }
}
