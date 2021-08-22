package com.example.facialexpressionchampionship.extension

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.facialexpressionchampionship.R
import timber.log.Timber
import java.io.*

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

fun Fragment.registerForActivityResult(success: (String) -> Unit , error: (Int) -> Unit): ActivityResultLauncher<Intent> {
    return registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
        if (result?.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                try {
                    intent.data?.let {
                        val path = requireContext().getPathFromUri(it)
                        if (path != null) {
                            success(path)
                        } else{
                            error(R.string.failed_get_image_path)
                        }
                    }
                } catch (e: IOException) {
                    Timber.e("画像取得エラー ${e.message}")
                    error(R.string.failed_get_image_path)
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

fun Fragment.getImageBytes(filePath: String?): ByteArray? {
    val file = File(filePath)

    try {
        val inputStream = FileInputStream(file)
        val byteArrayOutputStream = ByteArrayOutputStream()

        // 8byteずつ読み込む
        val buffer = ByteArray(8)
        // データの読み込み
        var readSize = inputStream.read(buffer)

        // 全てのデータを読み込むまで繰り返す
        while (readSize != -1) {
            // 読み込んだデータを書き込む
            byteArrayOutputStream.write(buffer, 0, readSize)
            // データの更新
            readSize = inputStream.read(buffer)
        }
        val totalByteArray = byteArrayOutputStream.toByteArray()

        inputStream.close()
        byteArrayOutputStream.close()

        return totalByteArray

    } catch (e: Exception) {
        Timber.e("Byte変換エラー $e")
        return null
    }
}
