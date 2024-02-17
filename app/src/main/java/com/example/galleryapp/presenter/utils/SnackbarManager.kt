package com.example.galleryapp.presenter.utils

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import com.example.galleryapp.R
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SnackbarManager @Inject constructor() {

    fun showSnackbar(@StringRes messageResId: Int, rootView: View? = null) {
        rootView?.let { Snackbar.make(it, messageResId, Snackbar.LENGTH_SHORT).show() }
    }

    fun showSnackbar(
        message: String,
        rootView: View? = null,
    ) {
        rootView?.let {
            val snackbar = Snackbar.make(it, message, Snackbar.LENGTH_SHORT)

            val params = snackbar.view.layoutParams as CoordinatorLayout.LayoutParams
            params.gravity = Gravity.BOTTOM
            snackbar.view.layoutParams = params

            snackbar.show()
        }
    }
}