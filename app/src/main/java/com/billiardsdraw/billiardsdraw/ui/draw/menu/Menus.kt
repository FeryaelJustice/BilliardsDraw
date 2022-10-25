package com.billiardsdraw.billiardsdraw.ui.draw.menu

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.billiardsdraw.billiardsdraw.R
import com.billiardsdraw.billiardsdraw.data.model.draw.PathProperties
import com.billiardsdraw.billiardsdraw.ui.draw.ColorSlider
import com.billiardsdraw.billiardsdraw.ui.draw.ColorWheel
import com.billiardsdraw.billiardsdraw.ui.draw.DrawMode
import com.billiardsdraw.billiardsdraw.ui.theme.Blue400
import kotlin.math.roundToInt

// DRAW

@Composable
fun DrawingPropertiesMenu(
    modifier: Modifier = Modifier,
    pathProperties: PathProperties,
    drawMode: DrawMode,
    onUndo: () -> Unit,
    onRedo: () -> Unit,
    onPathPropertiesChange: (PathProperties) -> Unit,
    onDrawModeChanged: (DrawMode) -> Unit
) {

    var isOpened by rememberSaveable { mutableStateOf(true) }

    val properties by rememberUpdatedState(newValue = pathProperties)

    var showColorDialog by remember { mutableStateOf(false) }
    var showPropertiesDialog by remember { mutableStateOf(false) }
    var currentDrawMode = drawMode

    LazyRow(
        modifier = if (isOpened) modifier.fillMaxWidth() else modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if (isOpened) {
            item {
                Row {
                    IconButton(
                        onClick = {
                            currentDrawMode = if (currentDrawMode == DrawMode.Touch) {
                                DrawMode.Draw
                            } else {
                                DrawMode.Touch
                            }
                            onDrawModeChanged(currentDrawMode)
                        }
                    ) {
                        Icon(
                            Icons.Filled.TouchApp,
                            contentDescription = null,
                            tint = if (currentDrawMode == DrawMode.Touch) Color.Black else Color.LightGray
                        )
                    }
                    IconButton(
                        onClick = {
                            currentDrawMode = if (currentDrawMode == DrawMode.Erase) {
                                DrawMode.Draw
                            } else {
                                DrawMode.Erase
                            }
                            onDrawModeChanged(currentDrawMode)
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_eraser_black_24dp),
                            contentDescription = null,
                            tint = if (currentDrawMode == DrawMode.Erase) Color.Black else Color.LightGray
                        )
                    }


                    IconButton(onClick = { showColorDialog = !showColorDialog }) {
                        ColorWheel(modifier = Modifier.size(24.dp))
                    }

                    IconButton(onClick = { showPropertiesDialog = !showPropertiesDialog }) {
                        Icon(Icons.Filled.Brush, contentDescription = null, tint = Color.LightGray)
                    }

                    IconButton(onClick = {
                        onUndo()
                    }) {
                        Icon(Icons.Filled.Undo, contentDescription = null, tint = Color.LightGray)
                    }

                    IconButton(onClick = {
                        onRedo()
                    }) {
                        Icon(Icons.Filled.Redo, contentDescription = null, tint = Color.LightGray)
                    }
                }
            }
        }
        item {
            if (isOpened) {
                IconButton(onClick = { isOpened = isOpened.not() }) {
                    Icon(Icons.Filled.Close, contentDescription = null, tint = Color.LightGray)
                }
            } else {
                IconButton(onClick = { isOpened = isOpened.not() }) {
                    Icon(
                        Icons.Filled.ArrowUpward,
                        contentDescription = null,
                        tint = Color.LightGray
                    )
                }
            }
        }
    }

    if (showColorDialog) {
        ColorSelectionDialog(
            properties.color,
            onDismiss = { showColorDialog = !showColorDialog },
            onNegativeClick = { showColorDialog = !showColorDialog },
            onPositiveClick = { color: Color ->
                showColorDialog = !showColorDialog
                properties.color = color
            }
        )
    }

    if (showPropertiesDialog) {
        PropertiesMenuDialog(properties) {
            showPropertiesDialog = !showPropertiesDialog
        }
    }
}

@Composable
fun PropertiesMenuDialog(pathOption: PathProperties, onDismiss: () -> Unit) {

    var strokeWidth by remember { mutableStateOf(pathOption.strokeWidth) }
    var strokeCap by remember { mutableStateOf(pathOption.strokeCap) }
    var strokeJoin by remember { mutableStateOf(pathOption.strokeJoin) }

    Dialog(onDismissRequest = onDismiss) {

        Card(
            elevation = 2.dp,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {

                Text(
                    text = "Properties",
                    color = Blue400,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 12.dp, top = 12.dp)
                )

                Canvas(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 20.dp)
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    val path = Path()
                    path.moveTo(0f, size.height / 2)
                    path.lineTo(size.width, size.height / 2)

                    drawPath(
                        color = pathOption.color,
                        path = path,
                        style = Stroke(
                            width = strokeWidth,
                            cap = strokeCap,
                            join = strokeJoin
                        )
                    )
                }

                Text(
                    text = "Stroke Width ${strokeWidth.toInt()}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Slider(
                    value = strokeWidth,
                    onValueChange = {
                        strokeWidth = it
                        pathOption.strokeWidth = strokeWidth
                    },
                    valueRange = 1f..100f,
                    onValueChangeFinished = {}
                )


                ExposedSelectionMenu(title = "Stroke Cap",
                    index = when (strokeCap) {
                        StrokeCap.Butt -> 0
                        StrokeCap.Round -> 1
                        else -> 2
                    },
                    options = listOf("Butt", "Round", "Square"),
                    onSelected = {
                        println("STOKE CAP $it")
                        strokeCap = when (it) {
                            0 -> StrokeCap.Butt
                            1 -> StrokeCap.Round
                            else -> StrokeCap.Square
                        }

                        pathOption.strokeCap = strokeCap

                    }
                )

                ExposedSelectionMenu(title = "Stroke Join",
                    index = when (strokeJoin) {
                        StrokeJoin.Miter -> 0
                        StrokeJoin.Round -> 1
                        else -> 2
                    },
                    options = listOf("Miter", "Round", "Bevel"),
                    onSelected = {
                        println("STOKE JOIN $it")

                        strokeJoin = when (it) {
                            0 -> StrokeJoin.Miter
                            1 -> StrokeJoin.Round
                            else -> StrokeJoin.Bevel
                        }

                        pathOption.strokeJoin = strokeJoin
                    }
                )
            }
        }
    }
}


@Composable
fun ColorSelectionDialog(
    initialColor: Color,
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: (Color) -> Unit
) {
    var red by remember { mutableStateOf(initialColor.red * 255) }
    var green by remember { mutableStateOf(initialColor.green * 255) }
    var blue by remember { mutableStateOf(initialColor.blue * 255) }
    var alpha by remember { mutableStateOf(initialColor.alpha * 255) }

    val color = Color(
        red = red.roundToInt(),
        green = green.roundToInt(),
        blue = blue.roundToInt(),
        alpha = alpha.roundToInt()
    )

    Dialog(onDismissRequest = onDismiss) {

        BoxWithConstraints(
            Modifier
                .shadow(1.dp, RoundedCornerShape(8.dp))
                .background(Color.White)
        ) {

            val widthInDp = LocalDensity.current.run { maxWidth }


            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    text = "Color",
                    color = Blue400,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 12.dp)
                )

                // Initial and Current Colors
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp, vertical = 20.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .background(
                                initialColor,
                                shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                            )
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .background(
                                color,
                                shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                            )
                    )
                }

                ColorWheel(
                    modifier = Modifier
                        .width(widthInDp * .8f)
                        .aspectRatio(1f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Sliders
                ColorSlider(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp)
                        .fillMaxWidth(),
                    title = "Red",
                    titleColor = Color.Red,
                    rgb = red,
                    onColorChanged = {
                        red = it
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
                ColorSlider(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp)
                        .fillMaxWidth(),
                    title = "Green",
                    titleColor = Color.Green,
                    rgb = green,
                    onColorChanged = {
                        green = it
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))

                ColorSlider(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp)
                        .fillMaxWidth(),
                    title = "Blue",
                    titleColor = Color.Blue,
                    rgb = blue,
                    onColorChanged = {
                        blue = it
                    }
                )

                Spacer(modifier = Modifier.height(4.dp))

                ColorSlider(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp)
                        .fillMaxWidth(),
                    title = "Alpha",
                    titleColor = Color.Black,
                    rgb = alpha,
                    onColorChanged = {
                        alpha = it
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Buttons

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(Color(0xffF3E5F5)),
                    verticalAlignment = Alignment.CenterVertically

                ) {

                    TextButton(
                        onClick = onNegativeClick,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        Text(text = "CANCEL")
                    }
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        onClick = {
                            onPositiveClick(color)
                        },
                    ) {
                        Text(text = "OK")
                    }
                }
            }
        }
    }
}

/**
 * Expandable selection menu
 * @param title of the displayed item on top
 * @param index index of selected item
 * @param options list of [String] options
 * @param onSelected lambda to be invoked when an item is selected that returns
 * its index.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExposedSelectionMenu(
    title: String,
    index: Int,
    options: List<String>,
    onSelected: (Int) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[index]) }
    var selectedIndex = remember { index }

    ExposedDropdownMenuBox(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = { Text(title) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            )
        )
        ExposedDropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = {
                expanded = false

            }
        ) {
            options.forEachIndexed { index: Int, selectionOption: String ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                        selectedIndex = index
                        onSelected(selectedIndex)
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
}

// BOTTOM MENU DRAW
@Composable
fun BottomMenuDraw(modifier: Modifier = Modifier) {
    var bolaEfectoState by remember { mutableStateOf(false) } // false if bola efecto lateral, true if empty
    var tomaDeBolaState by remember { mutableStateOf(5) }
    var showTomaDeBolaDialog by remember { mutableStateOf(false) }
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black)
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    painter = if (!bolaEfectoState) painterResource(id = R.drawable.bolaefecto_iconolateral) else painterResource(
                        id = R.drawable.bolaefecto
                    ),
                    contentDescription = "Bola effect menu",
                    modifier = Modifier.clickable {
                        bolaEfectoState = bolaEfectoState.not()
                    }
                )
                Image(
                    painter = getTomaDeBolaImage(tomaDeBolaState),
                    contentDescription = "Toma de bola menu",
                    modifier = Modifier.clickable {
                        showTomaDeBolaDialog = showTomaDeBolaDialog.not()
                    })
            }
        }
    }

    if (showTomaDeBolaDialog) {
        TomaDeBolaSelectionDialog(
            onDismiss = { showTomaDeBolaDialog = showTomaDeBolaDialog.not() },
            onClick = { selectedBallNumber: Int ->
                showTomaDeBolaDialog = showTomaDeBolaDialog.not()
                tomaDeBolaState = selectedBallNumber
            }
        )
    }
}

@Composable
fun TomaDeBolaSelectionDialog(
    onDismiss: () -> Unit,
    onClick: (Int) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        BoxWithConstraints(
            Modifier
                .shadow(1.dp, RoundedCornerShape(8.dp))
                .background(Color.Black)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(modifier = Modifier.clickable { onClick(1) }) {
                    Image(
                        painter = painterResource(id = R.drawable.tomadebola_one),
                        contentDescription = "Toma de bola one"
                    )
                    Text(text = "MUY FINA")
                }
                Row(modifier = Modifier.clickable { onClick(2) }) {
                    Image(
                        painter = painterResource(id = R.drawable.tomadebola_two),
                        contentDescription = "Toma de bola two"
                    )
                    Text(text = "1/8 BOLA")
                }
                Row(modifier = Modifier.clickable { onClick(3) }) {
                    Image(
                        painter = painterResource(id = R.drawable.tomadebola_three),
                        contentDescription = "Toma de bola three"
                    )
                    Text(text = "POCA BOLA")
                }
                Row(modifier = Modifier.clickable { onClick(4) }) {
                    Image(
                        painter = painterResource(id = R.drawable.tomadebola_four),
                        contentDescription = "Toma de bola four"
                    )
                    Text(text = "3/8 BOLA")
                }
                Row(modifier = Modifier.clickable { onClick(5) }) {
                    Image(
                        painter = painterResource(id = R.drawable.tomadebola_halfball),
                        contentDescription = "Toma de bola half ball"
                    )
                    Text(text = "MEDIA BOLA")
                }
                Row(modifier = Modifier.clickable { onClick(6) }) {
                    Image(
                        painter = painterResource(id = R.drawable.tomadebola_five),
                        contentDescription = "Toma de bola five"
                    )
                    Text(text = "5/8 BOLA")
                }
                Row(modifier = Modifier.clickable { onClick(7) }) {
                    Image(
                        painter = painterResource(id = R.drawable.tomadebola_six),
                        contentDescription = "Toma de bola six"
                    )
                    Text(text = "MUCHA BOLA")
                }
                Row(modifier = Modifier.clickable { onClick(8) }) {
                    Image(
                        painter = painterResource(id = R.drawable.tomadebola_seven),
                        contentDescription = "Toma de bola seven"
                    )
                    Text(text = "7/8 BOLA")
                }
                Row(modifier = Modifier.clickable { onClick(9) }) {
                    Image(
                        painter = painterResource(id = R.drawable.tomadebola_eight),
                        contentDescription = "Toma de bola eight"
                    )
                    Text(text = "BOLA LLENA")
                }
            }
        }
    }
}

@Composable
fun getTomaDeBolaImage(number: Int): Painter {
    return when (number) {
        1 -> painterResource(id = R.drawable.tomadebola_one)
        2 -> painterResource(id = R.drawable.tomadebola_two)
        3 -> painterResource(id = R.drawable.tomadebola_three)
        4 -> painterResource(id = R.drawable.tomadebola_four)
        5 -> painterResource(id = R.drawable.tomadebola_halfball)
        6 -> painterResource(id = R.drawable.tomadebola_five)
        7 -> painterResource(id = R.drawable.tomadebola_six)
        8 -> painterResource(id = R.drawable.tomadebola_seven)
        9 -> painterResource(id = R.drawable.tomadebola_eight)
        else -> painterResource(id = R.drawable.tomadebola_one)
    }
}