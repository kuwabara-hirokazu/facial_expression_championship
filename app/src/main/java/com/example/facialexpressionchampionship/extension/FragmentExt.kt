package com.example.facialexpressionchampionship.extension

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.model.Failure
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import java.io.IOException

private const val IMAGE_TYPE = "image/*"

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

fun Fragment.registerForActivityResult(success: (Uri) -> Unit): ActivityResultLauncher<Intent> {
    return registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
        if (result?.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                try {
                    intent.data?.let { success(it) }
                } catch (e: IOException) {
                    Timber.e("画像取得エラー ${e.message}")
                }
            }
        }
    }
}

fun Fragment.openLibrary(launcher: ActivityResultLauncher<Intent>) {
    Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = IMAGE_TYPE
        launcher.launch(this)
    }
}
