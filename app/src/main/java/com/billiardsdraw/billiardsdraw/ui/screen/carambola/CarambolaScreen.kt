package com.billiardsdraw.billiardsdraw.ui.screen.carambola

// import android.graphics.Paint
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
// import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.R
import com.billiardsdraw.billiardsdraw.data.model.draw.PathProperties
import com.billiardsdraw.billiardsdraw.gesture.dragMotionEvent
import com.billiardsdraw.billiardsdraw.ui.draw.DrawMode
import com.billiardsdraw.billiardsdraw.ui.draw.menu.DrawingPropertiesMenu
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
import com.billiardsdraw.billiardsdraw.ui.screen.pool.PoolScreenViewModel
import com.billiardsdraw.billiardsdraw.ui.util.showToastShort
import com.billiardsdraw.billiardsdraw.gesture.MotionEvent
import kotlinx.coroutines.CoroutineScope

/***
 * Support code based from https://github.com/SmartToolFactory/Compose-Drawing-App (NOT THE SAME CODE, ADAPTED)
 */

@Composable
fun CarambolaScreen(
    viewModel: PoolScreenViewModel,
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    appViewModel: BilliardsDrawViewModel
) {

    // PREVIOUS OWN CODE:
    // Check is Logged
    LaunchedEffect(key1 = "loginCheck", block = {
        if (!appViewModel.isSignedIn()) {
            navigateClearingAllBackstack(navController, Routes.GeneralApp.route)
        }
    })
    // Scale, for zoom
    val scale = remember { mutableStateOf(1f) }
    val rotationState = remember { mutableStateOf(0f) }
    val isResetZoomRotation = rememberSaveable { mutableStateOf(false) }
    isResetZoomRotation.value = scale.value != 1f || rotationState.value != 0f
    // Color picker (if we can use it, still not used, its an external library)
    // val wheelController = rememberColorPickerController()
    // val wheelVisible = rememberSaveable { mutableStateOf(false) }

    // NEW OWN CODE BASED IN LIBRARY of SmartToolFactory: Compose Drawing App
    val context = LocalContext.current
    val paths = remember { mutableStateListOf<Pair<Path, PathProperties>>() }
    val pathsUndone = remember { mutableStateListOf<Pair<Path, PathProperties>>() }
    var motionEvent by remember { mutableStateOf(com.billiardsdraw.billiardsdraw.gesture.MotionEvent.Idle) }
    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }
    var previousPosition by remember { mutableStateOf(Offset.Unspecified) }
    var drawMode by remember { mutableStateOf(DrawMode.Draw) }
    var currentPath by remember { mutableStateOf(Path()) }
    var currentPathProperty by remember { mutableStateOf(PathProperties()) }
    // Working on
    // var currentBall by remember { mutableStateOf(Ball()) }

    // showToastShort(context = context, message = "Screen with: ${appViewModel.windowInfo.value?.screenWidth} / Screen Height: ${appViewModel.windowInfo.value?.screenHeight}")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTransformGestures { _, _, zoom, rotation ->
                    scale.value *= zoom
                    rotationState.value += rotation

                    isResetZoomRotation.value = scale.value != 1f || rotationState.value != 0f
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val drawModifier = Modifier
                    .padding(start = appViewModel.windowInfo.value?.screenWidth!! / 7, top = 0.dp, end = appViewModel.windowInfo.value?.screenWidth!! / 7, bottom = 0.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .dragMotionEvent(
                        onDragStart = { pointerInputChange ->
                            motionEvent = com.billiardsdraw.billiardsdraw.gesture.MotionEvent.Down
                            currentPosition = pointerInputChange.position
                            if (pointerInputChange.pressed != pointerInputChange.previousPressed) pointerInputChange.consume()
                        },
                        onDrag = { pointerInputChange ->
                            motionEvent = com.billiardsdraw.billiardsdraw.gesture.MotionEvent.Move
                            currentPosition = pointerInputChange.position

                            if (drawMode == DrawMode.Touch) {
                                val change = pointerInputChange.positionChange()
                                println("DRAG: $change")
                                paths.forEach { entry ->
                                    val path: Path = entry.first
                                    path.translate(change)
                                }
                                currentPath.translate(change)
                            }
                            if (pointerInputChange.positionChange() != Offset.Zero) pointerInputChange.consume()
                        },
                        onDragEnd = { pointerInputChange ->
                            motionEvent = com.billiardsdraw.billiardsdraw.gesture.MotionEvent.Up
                            if (pointerInputChange.pressed != pointerInputChange.previousPressed) pointerInputChange.consume()
                        }
                    )
                // Is there is zoom or rotation
                ExtendedFloatingActionButton(
                    text = {
                        if (isResetZoomRotation.value) Text("Reset")
                    },
                    onClick = {
                        if (isResetZoomRotation.value) {
                            scale.value = 1f
                            rotationState.value = 0f
                        }
                    },
                    shape = RoundedCornerShape(
                        ZeroCornerSize
                    ),
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_baseline_360_24),
                            contentDescription = if (isResetZoomRotation.value) "Reset" else ""
                        )
                    },
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
                )
                Canvas(modifier = drawModifier) {
                    when (motionEvent) {
                        MotionEvent.Down -> {
                            if (drawMode != DrawMode.Touch) {
                                currentPath.moveTo(currentPosition.x, currentPosition.y)
                            }
                            previousPosition = currentPosition
                        }
                        MotionEvent.Move -> {
                            if (drawMode != DrawMode.Touch) {
                                currentPath.quadraticBezierTo(
                                    previousPosition.x,
                                    previousPosition.y,
                                    (previousPosition.x + currentPosition.x) / 2,
                                    (previousPosition.y + currentPosition.y) / 2

                                )
                            }
                            previousPosition = currentPosition
                        }
                        MotionEvent.Up -> {
                            if (drawMode != DrawMode.Touch) {
                                currentPath.lineTo(currentPosition.x, currentPosition.y)

                                // Pointer is up save current path
                                // paths[currentPath] = currentPathProperty
                                paths.add(Pair(currentPath, currentPathProperty))

                                // Since paths are keys for map, use new one for each key
                                // and have separate path for each down-move-up gesture cycle
                                currentPath = Path()

                                // Create new instance of path properties to have new path and properties
                                // only for the one currently being drawn
                                currentPathProperty = PathProperties(
                                    strokeWidth = currentPathProperty.strokeWidth,
                                    color = currentPathProperty.color,
                                    strokeCap = currentPathProperty.strokeCap,
                                    strokeJoin = currentPathProperty.strokeJoin,
                                    eraseMode = currentPathProperty.eraseMode
                                )
                            }

                            // Since new path is drawn no need to store paths to undone
                            pathsUndone.clear()

                            // If we leave this state at MotionEvent.Up it causes current path to draw
                            // line from (0,0) if this composable recomposes when draw mode is changed
                            currentPosition = Offset.Unspecified
                            previousPosition = currentPosition
                            motionEvent = com.billiardsdraw.billiardsdraw.gesture.MotionEvent.Idle
                        }
                        else -> Unit
                    }
                    with(drawContext.canvas.nativeCanvas) {
                        val checkPoint = saveLayer(null, null)
                        paths.forEach {
                            val path = it.first
                            val property = it.second
                            if (!property.eraseMode) {
                                drawPath(
                                    color = property.color,
                                    path = path,
                                    style = Stroke(
                                        width = property.strokeWidth,
                                        cap = property.strokeCap,
                                        join = property.strokeJoin
                                    )
                                )
                            } else {
                                // Source
                                drawPath(
                                    color = Color.Transparent,
                                    path = path,
                                    style = Stroke(
                                        width = currentPathProperty.strokeWidth,
                                        cap = currentPathProperty.strokeCap,
                                        join = currentPathProperty.strokeJoin
                                    ),
                                    blendMode = BlendMode.Clear
                                )
                            }
                        }
                        if (motionEvent != com.billiardsdraw.billiardsdraw.gesture.MotionEvent.Idle) {
                            if (!currentPathProperty.eraseMode) {
                                drawPath(
                                    color = currentPathProperty.color,
                                    path = currentPath,
                                    style = Stroke(
                                        width = currentPathProperty.strokeWidth,
                                        cap = currentPathProperty.strokeCap,
                                        join = currentPathProperty.strokeJoin
                                    )
                                )
                            } else {
                                drawPath(
                                    color = Color.Transparent,
                                    path = currentPath,
                                    style = Stroke(
                                        width = currentPathProperty.strokeWidth,
                                        cap = currentPathProperty.strokeCap,
                                        join = currentPathProperty.strokeJoin
                                    ),
                                    blendMode = BlendMode.Clear
                                )
                            }
                        }
                        restoreToCount(checkPoint)
                    }
                }

                DrawingPropertiesMenu(
                    modifier = Modifier
                        .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                        .shadow(1.dp, RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .padding(4.dp),
                    pathProperties = currentPathProperty,
                    drawMode = drawMode,
                    onUndo = {
                        if (paths.isNotEmpty()) {
                            val lastItem = paths.last()
                            val lastPath = lastItem.first
                            val lastPathProperty = lastItem.second
                            paths.remove(lastItem)
                            pathsUndone.add(Pair(lastPath, lastPathProperty))
                        }
                    },
                    onRedo = {
                        if (pathsUndone.isNotEmpty()) {
                            val lastPath = pathsUndone.last().first
                            val lastPathProperty = pathsUndone.last().second
                            pathsUndone.removeLast()
                            paths.add(Pair(lastPath, lastPathProperty))
                        }
                    },
                    onPathPropertiesChange = {
                        motionEvent = com.billiardsdraw.billiardsdraw.gesture.MotionEvent.Idle
                    },
                    onDrawModeChanged = {
                        motionEvent = com.billiardsdraw.billiardsdraw.gesture.MotionEvent.Idle
                        drawMode = it
                        currentPathProperty.eraseMode = (drawMode == DrawMode.Erase)
                        Toast.makeText(
                            context,
                            "pathProperty: ${currentPathProperty.hashCode()}, " +
                                    "Erase Mode: ${currentPathProperty.eraseMode}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        }
    }
}