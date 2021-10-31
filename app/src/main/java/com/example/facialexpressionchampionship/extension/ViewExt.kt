package com.example.facialexpressionchampionship.extension

import android.view.View
import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.model.Failure
import com.google.android.material.snackbar.Snackbar

fun View.showError(failure: Failure) {
    Snackbar.make(this, failure.message, Snackbar.LENGTH_LONG)
        .setAction(R.string.retry) { failure.retry() }
        .show()
}
