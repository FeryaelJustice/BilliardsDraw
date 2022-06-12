package com.billiardsdraw.billiardsdraw.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun BoardScreen(navController: NavHostController){
    val controller = rememberColorPickerController()
    controller.setWheelRadius(40.dp) // set the radius size of the wheel.
    controller.setWheelColor(Color.Blue) // set the color of the wheel.
    controller.setWheelAlpha(0.5f) // set the transparency of the wheel.
    // controller.setWheelImageBitmap(imageBitmap) // set the wheel image with your custom ImageBitmap.
    controller.selectByCoordinate(x = 100f, y = 100f, fromUser = false) // select x = 100, y = 100.
    controller.selectCenter(fromUser = false) // select center of the palette.
    controller.setDebounceDuration(300L)
    controller.setEnabled(true)


    HsvColorPicker(
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)
            .padding(10.dp),
        controller = controller,
        onColorChanged = { colorEnvelope: ColorEnvelope ->
            // do something
            val color: Color = colorEnvelope.color // ARGB color value.
            val hexCode: String =
                colorEnvelope.hexCode // Color hex code, which represents color value.
            val fromUser: Boolean =
                colorEnvelope.fromUser // Represents this event is triggered by user or not.
        }
    )
}