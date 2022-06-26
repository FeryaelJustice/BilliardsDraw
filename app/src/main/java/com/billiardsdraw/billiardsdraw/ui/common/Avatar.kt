package com.billiardsdraw.billiardsdraw.ui.common

import android.net.Uri
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.billiardsdraw.billiardsdraw.R

@Composable
fun Avatar(uri: Uri){
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(uri.path)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.logo),
        contentDescription = stringResource(R.string.default_image_description),
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(64.dp).clip(CircleShape)
    )
}