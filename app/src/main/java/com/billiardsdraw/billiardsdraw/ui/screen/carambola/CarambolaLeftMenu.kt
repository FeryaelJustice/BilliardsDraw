package com.billiardsdraw.billiardsdraw.ui.screen.carambola

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
import com.billiardsdraw.billiardsdraw.ui.util.showToastShort
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker

@Composable
fun CarambolaLeftMenu(
    viewModel: BilliardsDrawViewModel,
    modifier: Modifier,
    navController: NavHostController,
    context: Context,
    wheelController: ColorPickerController,
    wheelVisible: Boolean = true,
    onWheelVisibleClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        if (wheelVisible) {
            HsvColorPicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .width(40.dp)
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
                    viewModel.selectedCarambolaCanvasColor = hexCode
                }
            )
        }
        Button(onClick = {
            onWheelVisibleClick()
        }) {
            if (wheelVisible) {
                Text(text = "Close Color")
            } else {
                Text(text = "Open Color")
            }
        }
        Button(onClick = {
            navigateClearingAllBackstack(
                navController,
                Routes.LoginScreen.route
            )
        }) {
            Text(text = "Main screen")
        }
    }
}

private fun setWheelController(controller: ColorPickerController) {
    controller.setWheelRadius(40.dp) // set the radius size of the wheel.
    controller.setWheelColor(Color.Blue) // set the color of the wheel.
    controller.setWheelAlpha(0.5f) // set the transparency of the wheel.
// controller.setWheelImageBitmap(imageBitmap) // set the wheel image with your custom ImageBitmap.
    controller.selectByCoordinate(x = 100f, y = 100f, fromUser = false) // select x = 100, y = 100.
    controller.selectCenter(fromUser = false) // select center of the palette.
    controller.setDebounceDuration(300L)
    controller.setEnabled(true)
}