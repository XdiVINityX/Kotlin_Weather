package com.example.kotlin_weather.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar


fun View.showSnackbarWithAction(text: String, actionText: String, action: (View) -> Unit) {
    Snackbar.make(this, text, Snackbar.LENGTH_INDEFINITE)
        .setAction(actionText, action)
        .show()
}

fun View.showEr(text: String, actionText: String, action: (View) -> Unit) {
    Snackbar.make(this, text, Snackbar.LENGTH_INDEFINITE)
        .setAction(actionText, action)
        .show()
}

fun View.showSnackbarWithoutAction(stringTextId: Int) {
    Snackbar.make(this, stringTextId, Snackbar.LENGTH_LONG).show()
}
