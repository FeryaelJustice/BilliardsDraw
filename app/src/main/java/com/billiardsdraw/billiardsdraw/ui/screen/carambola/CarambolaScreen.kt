package com.billiardsdraw.billiardsdraw.ui.screen.carambola

import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.R
import com.billiardsdraw.billiardsdraw.common.HexToJetpackColor
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CarambolaScreen(
    viewModel: CarambolaScreenViewModel,
    navController: NavHostController,
    appViewModel: BilliardsDrawViewModel
) {

    // Scale, for zoom
    val scale = remember { mutableStateOf(1f) }
    val rotationState = remember { mutableStateOf(0f) }

    // Context
    val context = LocalContext.current
    // Color picker
    val wheelController = rememberColorPickerController()
    val wheelVisible = rememberSaveable { mutableStateOf(false) }
    // Canvas
    val canvasPath = Path()
    val canvasAction: MutableState<Any?> = rememberSaveable { mutableStateOf(null) }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    scale.value *= zoom
                    rotationState.value += rotation
                }
            }
    ) {
        Card(
            elevation = 0.dp,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(8.dp)
                .graphicsLayer {
                    // adding some zoom limits (min 100%, max 200%)
                    scaleX = maxOf(1f, minOf(3f, scale.value))
                    scaleY = maxOf(1f, minOf(3f, scale.value))
                    rotationZ = rotationState.value
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.carambola_azul),
                contentDescription = stringResource(id = R.string.backgroundScreenDescription),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            // Lazy hace que librerias externas no se vean
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CarambolaLeftMenu(
                    viewModel = viewModel,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(40.dp),
                    navController = navController,
                    context = context,
                    wheelController = wheelController,
                    wheelVisible = wheelVisible.value,
                    onWheelVisibleClick = {
                        wheelVisible.value = !wheelVisible.value
                    })
                CarambolaCanvas(modifier = Modifier
                    .fillMaxHeight()
                    .width(200.dp)
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                canvasAction.value = it
                                canvasPath.moveTo(it.x, it.y)
                            }
                            MotionEvent.ACTION_MOVE -> {
                                // Here is where drawing event is triggered
                                canvasAction.value = it
                                canvasPath.lineTo(it.x, it.y)
                            }
                            MotionEvent.ACTION_UP -> {
                            }
                        }
                        true
                    },
                    onDraw = {
                        canvasAction.value?.let {
                            drawPath(
                                path = canvasPath,
                                color = HexToJetpackColor.getColor(viewModel.selectedCarambolaCanvasColor),
                                alpha = 1f,
                                style = Stroke(10f)
                            )
                        }
                    })
                CarambolaRightMenu(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(40.dp),
                    navController = navController,
                    context = context
                )
            }
        }
    }
}