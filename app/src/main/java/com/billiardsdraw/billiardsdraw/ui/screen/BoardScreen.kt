package com.billiardsdraw.billiardsdraw.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.billiardsdraw.billiardsdraw.ui.util.showToastShort
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun BoardScreen(navController: NavHostController) {
    val context = LocalContext.current
    val wheelVisible = rememberSaveable { mutableStateOf(true) }
    val controller = rememberColorPickerController()
    /*
controller.setWheelRadius(40.dp) // set the radius size of the wheel.
controller.setWheelColor(Color.Blue) // set the color of the wheel.
controller.setWheelAlpha(0.5f) // set the transparency of the wheel.
// controller.setWheelImageBitmap(imageBitmap) // set the wheel image with your custom ImageBitmap.
controller.selectByCoordinate(x = 100f, y = 100f, fromUser = false) // select x = 100, y = 100.
controller.selectCenter(fromUser = false) // select center of the palette.
controller.setDebounceDuration(300L)
controller.setEnabled(true)
*/
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            if (wheelVisible.value) {
                HsvColorPicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(2.dp),
                    controller = controller,
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
                wheelVisible.value = !wheelVisible.value
            }) {
                if (wheelVisible.value) {
                    Text(text = "Disable wheel")
                } else {
                    Text(text = "Enable wheel")
                }
            }
        }
    }
}

@Preview(name = "Board Screen")
@Composable
fun BoardScreenPreview() {
    BoardScreen(navController = rememberNavController())
}