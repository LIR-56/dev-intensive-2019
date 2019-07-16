package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.graphics.Rect
import android.util.Log


fun Activity.hideKeyboard() {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.isKeyboardOpen() : Boolean {
    val SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD = 128
    val rootView = window.decorView
    val r = Rect()
    rootView.getWindowVisibleDisplayFrame(r)
    val dm = rootView.resources.displayMetrics
    /* heightDiff = rootView height - status bar height (r.top) - visible frame height (r.bottom - r.top) */
    val heightDiff = rootView.bottom - r.bottom

    /* Threshold size: dp to pixels, multiply with display density */
    val isKeyboardShown = heightDiff > SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD * dm.density
    Log.d("M_Activity","KeyboardShown: $isKeyboardShown")
    return isKeyboardShown
}

fun Activity.isKeyboardClosed() = !isKeyboardOpen()