package com.billiardsdraw.billiardsdraw.ui.util

import android.content.Context
import android.widget.Toast

fun showToastShort(context: Context, message: String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun showToastLong(context: Context, message: String){
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}