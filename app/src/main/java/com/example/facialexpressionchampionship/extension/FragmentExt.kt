package com.example.facialexpressionchampionship.extension

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.model.Failure
import com.google.android.material.snackbar.Snackbar

fun Fragment.showFragment(fragmentManager: FragmentManager, @IdRes containerViewId: Int, isStack: Boolean) {
    if (isStack) {
        fragmentManager.beginTransaction()
            .replace(containerViewId, this)
            .addToBackStack(null)
            .commit()
    } else {
        fragmentManager.popBackStack()
        fragmentManager
            .beginTransaction()
            .replace(containerViewId, this)
            .commit()
    }
}

fun Fragment.showError(view: View, failure: Failure) {
    Snackbar.make(view, failure.message, Snackbar.LENGTH_LONG)
        .setAction(R.string.retry) { failure.retry() }
        .show()
}
