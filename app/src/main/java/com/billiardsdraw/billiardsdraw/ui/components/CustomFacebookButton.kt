package com.billiardsdraw.billiardsdraw.ui.components.login

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.billiardsdraw.billiardsdraw.R
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton

@Composable
fun CustomFacebookButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onSuccess: (LoginResult) -> Unit,
    onCancel: () -> Unit,
    onError: (FacebookException?) -> Unit,
) {
    val callbackManager = FacebookUtil.callbackManager
    val loginText = stringResource(R.string.txt_connect_with_facebook)
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        factory = ::LoginButton,
        update = { button ->
            button.setLoginText(loginText)
            button.setReadPermissions("email","public_profile")
            button.isEnabled = enabled

            button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    onSuccess(result)
                    Log.d("facebook", "Login : ${result.accessToken}")
                }

                override fun onCancel() {
                    onCancel()
                    Log.d("facebook", "Login : On Cancel")
                }

                override fun onError(error: FacebookException?) {
                    onError(error)
                    Log.d("facebook", "Login : ${error?.localizedMessage}")
                }
            })
        }
    )
}

object FacebookUtil {
    val callbackManager: CallbackManager by lazy {
        CallbackManager.Factory.create()
    }
}
