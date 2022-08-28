package com.billiardsdraw.billiardsdraw.ui.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.billiardsdraw.billiardsdraw.R

@Composable
fun EmailField(
    email: String,
    onTextFieldChanged: (String) -> Unit,
    context: Context,
    modifier: Modifier,
    enabled: Boolean
) {
    TextField(
        value = email, onValueChange = { onTextFieldChanged(it) },
        enabled = enabled,
        modifier = modifier.background(Color.White),
        label = { Text(text = context.resources.getString(R.string.email), color = Color.Black) },
        placeholder = {
            Text(
                text = context.resources.getString(R.string.email_hint),
                color = Color.Black
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun PasswordField(
    password: String,
    onTextFieldChanged: (String) -> Unit,
    context: Context,
    modifier: Modifier,
    enabled: Boolean,
    isRepeatPassword: Boolean = false
) {
    var visible by rememberSaveable { mutableStateOf(false) }
    TextField(
        value = password, onValueChange = { onTextFieldChanged(it) },
        enabled = enabled,
        modifier = modifier.background(Color.White),
        label = {
            Text(
                text = context.resources.getString(R.string.password),
                color = Color.Black
            )
        },
        placeholder = {
            Text(
                text = if (!isRepeatPassword) context.resources.getString(R.string.password_hint) else context.resources.getString(
                    R.string.repeatpassword_hint
                ),
                color = Color.Black
            )
        },
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        trailingIcon = {
            val image = if (visible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            // Please provide localized description for accessibility services
            val description =
                if (visible) "Hide password" else "Show password"

            IconButton(onClick = {
                visible = !visible
            }) {
                Icon(imageVector = image, description)
            }
        }
    )
}