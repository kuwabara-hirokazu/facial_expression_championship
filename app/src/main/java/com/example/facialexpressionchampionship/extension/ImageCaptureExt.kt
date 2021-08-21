package com.example.facialexpressionchampionship.extension

import android.media.MediaActionSound
import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.model.Failure
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
private const val IMAGE_FORMAT = ".jpg"

fun ImageCapture.takePicture(
    outputDirectory: File,
    success: (String) -> Unit,
    error: (Failure) -> Unit
) {
    // 保存先と保存名の設定
    val photoFile = File(
        outputDirectory,
        SimpleDateFormat(FILENAME_FORMAT, Locale.JAPAN)
            .format(System.currentTimeMillis()) + IMAGE_FORMAT
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    // シャッター音の設定
    val sound = MediaActionSound()
    sound.load(MediaActionSound.SHUTTER_CLICK)

    takePicture(
        outputOptions,
        Executors.newSingleThreadExecutor(),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                sound.play(MediaActionSound.SHUTTER_CLICK)
                val photoUrl = Uri.fromFile(photoFile).path
                if (photoUrl != null) success(photoUrl)
            }

            override fun onError(exception: ImageCaptureException) {
                Timber.e("写真保存に失敗: ${exception.message}")
                error(Failure(exception, R.string.failed_save_image) {})
            }
        }
    )
}
