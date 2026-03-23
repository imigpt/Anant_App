package com.example.anantapp.ui.components

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

/**
 * Custom Shape for curved bottom header
 * Creates a smooth wave-like bottom curve
 */
class CurvedBottomShape(private val curveHeight: Density.() -> Float = { 48.dp.toPx() }) : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): androidx.compose.ui.graphics.Outline {
        val curveHeightPx = density.curveHeight()
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(0f, size.height - curveHeightPx)
            
            // Create a smooth curve at the bottom
            quadraticBezierTo(
                size.width / 2f,
                size.height,
                size.width,
                size.height - curveHeightPx
            )
            
            lineTo(size.width, 0f)
            close()
        }
        return androidx.compose.ui.graphics.Outline.Generic(path)
    }
}
