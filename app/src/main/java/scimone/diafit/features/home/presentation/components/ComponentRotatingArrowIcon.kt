package scimone.diafit.features.home.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp


@Composable
fun ComponentRotatingArrowIcon(inputValue: Float) {
    val rotationAngle = (-inputValue * 180f) // Scale inputValue to [0, 180] range

    Canvas(
        modifier = Modifier
            .size(50.dp)
            .rotate(rotationAngle)
    ) {
        drawArrow()
    }
}

fun DrawScope.drawArrow() {
    val center = size.minDimension / 2f
    val radius = center - 10.dp.toPx()
    val arrowColor = Color.White
    val arrowStrokeWidth = 3.dp.toPx()

    // Draw outlined circle
    drawCircle(
        color = arrowColor,
        radius = radius,
        center = Offset(center, center),
        style = Stroke(width = arrowStrokeWidth)
    )

    // Draw arrow line
    val arrowLength = radius * 1.1f

    val arrowStartX = center
    val arrowEndX = center

    val arrowStartY = center + arrowLength / 2
    val arrowEndY = center - arrowLength / 2

    drawLine(
        color = arrowColor,
        start = Offset(arrowStartX, arrowStartY - radius * 0.25f),
        end = Offset(arrowEndX, arrowEndY),
        strokeWidth = arrowStrokeWidth
    )

    // Draw arrowhead (triangle)
    drawArrowhead(Offset(arrowStartX, arrowStartY), arrowColor, radius)
}

fun DrawScope.drawArrowhead(position: Offset, color: Color, radius: Float) {
    val triangleHeight = radius * 0.35f
    val triangleBaseHalf = radius * 0.5f

    val path = Path().apply {
        moveTo(position.x, position.y) // Tip of the arrowhead
        lineTo(
            position.x + triangleHeight,
            position.y - triangleBaseHalf
        ) // Bottom right of the arrowhead
        lineTo(
            position.x - triangleHeight,
            position.y - triangleBaseHalf
        ) // Bottom left of the arrowhead
        close()
    }

    drawPath(
        path = path,
        color = color
    )
}
