package com.example.facialexpressionchampionship.viewmodel

import android.media.MediaActionSound
import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.lifecycle.ViewModel
import com.example.facialexpressionchampionship.R
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.PublishSubject
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import javax.inject.Inject

private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
private const val IMAGE_FORMAT = ".jpg"

@HiltViewModel
class CameraViewModel @Inject constructor() : ViewModel() {
    val imageUrl: PublishSubject<String> = PublishSubject.create()
    val error: PublishSubject<Int> = PublishSubject.create()

    fun takePhoto(imageCapture: ImageCapture?, outputDirectory: File) {

        val imageCapture = imageCapture ?: return

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

        // 撮影・保存処理
        imageCapture.takePicture(
            outputOptions,
            Executors.newSingleThreadExecutor(),
            object : ImageCapture.OnImageSavedCallback {

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    sound.play(MediaActionSound.SHUTTER_CLICK)
                    val photoUrl = Uri.fromFile(photoFile).toString()
                    imageUrl.onNext(photoUrl)
                }

                override fun onError(exception: ImageCaptureException) {
                    Timber.e("写真保存に失敗: ${exception.message}")
                    error.onNext(R.string.failed_save_image)
                }
            }
        )
    }
}
