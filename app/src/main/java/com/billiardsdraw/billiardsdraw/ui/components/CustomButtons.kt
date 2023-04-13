package com.billiardsdraw.billiardsdraw.ui.components

import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.billiardsdraw.billiardsdraw.R

@Composable
fun CustomGoogleButton(
    context: Context,
    modifier: Modifier = Modifier,
    loadingText: String = "Signing in...",
    icon: Int = R.drawable.ic_google_logo,
    borderColor: Color = Color.LightGray,
    backgroundColor: Color = MaterialTheme.colors.surface,
    progressIndicatorColor: Color = MaterialTheme.colors.primary,
    enabled: Boolean,
    onClicked: () -> Unit
) {
    var clicked by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier
            .clickable { clicked = !clicked },
        border = BorderStroke(width = 1.dp, color = borderColor),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Google Button",
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp, 24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = if (clicked) loadingText else context.resources.getString(R.string.google_sign_in))
            if (clicked && enabled) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp),
                    strokeWidth = 2.dp,
                    color = progressIndicatorColor
                )
                onClicked()
            }
        }
    }
}

@Composable
@Preview
private fun GoogleButtonPreview() {
    CustomGoogleButton(
        context = LocalContext.current,
        enabled = true,
        onClicked = {}
    )
}

@Composable
fun CustomSignInButton(
    context: Context,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClicked: () -> Unit
) {
    Button(
        onClick = onClicked,
        enabled = enabled,
        modifier = modifier,
    ) {
        Text(
            text = context.resources.getString(R.string.signIn)
        )
    }
}

@Composable
@Preview
private fun CustomSignInButtonPreview() {
    CustomSignInButton(
        context = LocalContext.current,
        enabled = true,
        onClicked = {}
    )
}

@Composable
fun CustomSignUpButton(
    context: Context,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClicked: () -> Unit
) {
    Button(
        onClick = onClicked,
        enabled = enabled,
        modifier = modifier,
    ) {
        Text(
            text = context.resources.getString(R.string.signUp)
        )
    }
}

@Composable
@Preview
private fun CustomSignUpButtonPreview() {
    CustomSignUpButton(
        context = LocalContext.current,
        enabled = true,
        onClicked = {}
    )
}