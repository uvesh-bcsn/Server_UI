package com.serverui.ui.debug

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val colors = listOf(Color.Green, Color.Blue, Color.Gray)

@Composable
fun randomColor(): Color {
    return remember { colors.random() }
}

@Composable
fun Modifier.debugBorder(color: Color = randomColor()): Modifier {
    return border(1.dp, color).padding(2.dp)
}