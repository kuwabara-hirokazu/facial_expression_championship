package com.example.facialexpressionchampionship.view

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaActionSound
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.databinding.ActivityCameraBinding
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CameraActivity : AppCompatActivity() {

    companion object {
        // 必要なパーミッションのリスト
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private val TAG = CameraActivity::class.java.simpleName
    }

    private var imageCapture: ImageCapture? = null

    private lateinit var outputDirectory: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityCameraBinding>(this, R.layout.activity_camera)

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        outputDirectory = getOutputDirectory()

        binding.cameraCaptureButton.setOnClickListener { takePhoto() }

    }

    // 全てのパーミッションが許可されているか
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this, R.string.camera_permission_message, Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            // プレビュー設定
            val preview = Preview.Builder()
                .build()
                .also { it.setSurfaceProvider(previewView.surfaceProvider) }

            imageCapture = ImageCapture.Builder().build()

            // デフォルトを内カメラに設定
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            try {
                cameraProvider.unbindAll()
                // ライフサイクルにカメラをバインディング
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)

            } catch (e: Exception) {
                Log.e(TAG, "バインディング失敗", e)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun getOutputDirectory(): File {
        // ストレージ/Android/media にディレクトリ作成
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        // 保存先と保存名の設定
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT,
                Locale.JAPAN
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // シャッター音の設定
        val sound = MediaActionSound()
        sound.load(MediaActionSound.SHUTTER_CLICK)

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val photoUri = Uri.fromFile(photoFile).toString()
                    Log.d(TAG, "写真保存に成功: $photoUri")
                    sound.play(MediaActionSound.SHUTTER_CLICK)
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e(TAG, "写真保存に失敗: ${exception.message}", exception)
                }
            }
        )
    }
}
