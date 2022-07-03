package com.billiardsdraw.billiardsdraw.ui.screen.pool

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.DrawScope

@Composable
fun PoolCanvas(
    modifier: Modifier,
    onDraw: (DrawScope.() -> Unit)
) {
    Canvas(modifier = modifier, onDraw = onDraw)
}