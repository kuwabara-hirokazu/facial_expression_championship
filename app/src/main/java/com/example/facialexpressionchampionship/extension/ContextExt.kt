package com.example.facialexpressionchampionship.extension

import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

fun Context.showToast(@StringRes resourceId: Int) {
    Toast.makeText(this, resourceId, Toast.LENGTH_SHORT).show()
}

fun Context.hasPermission(@NonNull permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Context.showConfirmDialog(@StringRes titleId: Int, @StringRes messageId: Int, ok:() -> Unit) {
    AlertDialog.Builder(this)
        .setTitle(titleId)
        .setMessage(messageId)
        .setPositiveButton(android.R.string.ok) { _, _ -> ok() }
        .setNegativeButton(android.R.string.cancel) { _, _ -> }
        .show()
}
