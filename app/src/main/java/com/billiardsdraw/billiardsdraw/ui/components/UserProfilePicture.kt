package com.billiardsdraw.billiardsdraw.ui.components

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.billiardsdraw.billiardsdraw.R

@Composable
fun UserProfilePicture(imageURL: Uri?, context: Context, onClick: () -> Unit) {
    var finalImageURI: Uri? = imageURL
    if (finalImageURI == null || finalImageURI == Uri.EMPTY) {
        finalImageURI = Uri.parse("android.resource://" + context.packageName + "/" + R.drawable.profileicon)
    }
    AsyncImage(
        model = finalImageURI,
        contentDescription = stringResource(R.string.profileicon),
        placeholder = painterResource(id = R.drawable.profileicon),
        contentScale = ContentScale.Crop,            // crop the image
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .clickable {
                onClick()
            }                       // clip to the circle shape
    )
}