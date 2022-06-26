package com.billiardsdraw.billiardsdraw.ui.screen.carambola

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.util.showToastShort
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker

@Composable
fun CarambolaLeftMenu(
    width: Dp = 60.dp,
    navController: NavHostController,
    context: Context,
    wheelController: ColorPickerController,
    wheelVisible: Boolean = true,
    onWheelVisibleClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(width),
        verticalArrangement = Arrangement.Center
    ) {
        if (wheelVisible) {
            HsvColorPicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .width(100.dp)
                    .padding(2.dp),
                controller = wheelController,
                onColorChanged = { colorEnvelope: ColorEnvelope ->
                    // do something
                    val color: Color = colorEnvelope.color // ARGB color value.
                    val hexCode: String =
                        colorEnvelope.hexCode // Color hex code, which represents color value.
                    val fromUser: Boolean =
                        colorEnvelope.fromUser // Represents this event is triggered by user or not.
                    showToastShort(context = context, hexCode)
                }
            )
        }
        Button(onClick = {
            onWheelVisibleClick()
        }) {
            if (wheelVisible) {
                Text(text = "Close draw wheel color picker")
            } else {
                Text(text = "Open draw wheel color picker")
            }
        }
        Button(onClick = { navController.navigate(Routes.MainScreen.route) }) {
            Text(text = "Go to main screen")
        }
    }
}

fun setWheelController(controller: ColorPickerController) {
    controller.setWheelRadius(40.dp) // set the radius size of the wheel.
    controller.setWheelColor(Color.Blue) // set the color of the wheel.
    controller.setWheelAlpha(0.5f) // set the transparency of the wheel.
// controller.setWheelImageBitmap(imageBitmap) // set the wheel image with your custom ImageBitmap.
    controller.selectByCoordinate(x = 100f, y = 100f, fromUser = false) // select x = 100, y = 100.
    controller.selectCenter(fromUser = false) // select center of the palette.
    controller.setDebounceDuration(300L)
    controller.setEnabled(true)
}