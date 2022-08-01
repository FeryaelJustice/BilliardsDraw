package com.billiardsdraw.billiardsdraw.ui.screen.contact

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.common.BILLIARDSDRAW_CONTACT_EMAIL
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactScreenViewModel @Inject constructor() : ViewModel() {
    //var email: String by mutableStateOf("")
    var subject: String by mutableStateOf("")
    var text: String by mutableStateOf("")

    fun send(navController: NavHostController, context: Context) {
        // val addresses = email.split(",".toRegex()).toTypedArray()
        val addresses = BILLIARDSDRAW_CONTACT_EMAIL.split(",".toRegex()).toTypedArray()
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, text)
        }
        /*
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
            navigate(navController, Routes.LoggedApp.route)
        }
         */
        context.startActivity(intent)
        navigate(navController, Routes.LoggedApp.route)
    }
}