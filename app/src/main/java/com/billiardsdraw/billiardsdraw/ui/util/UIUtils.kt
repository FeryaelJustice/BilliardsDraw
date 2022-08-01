package com.billiardsdraw.billiardsdraw.ui.util

import android.app.Activity
import android.content.Context
import android.widget.Toast

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Failure(val error: String?) : UiState<Nothing>()
}

enum class BilliardsDrawFontSize(val size: Int) {
    SMALL(8),
    MEDIUM(12),
    BIG(18),
    EXTRA_BIG(28)
}

fun showToastShort(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun showToastLong(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun Activity.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}