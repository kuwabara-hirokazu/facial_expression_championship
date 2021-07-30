package com.example.facialexpressionchampionship.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.databinding.ActivityCameraBinding
import com.example.facialexpressionchampionship.extension.showToast
import com.example.facialexpressionchampionship.viewmodel.CameraViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_camera.*
import timber.log.Timber
import java.io.File
import java.io.IOException

@AndroidEntryPoint
class CameraActivity : AppCompatActivity() {

    companion object {
        // 必要なパーミッションのリスト
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val IMAGE_TYPE = "image/*"
    }

    private var imageCapture: ImageCapture? = null

    private lateinit var outputDirectory: File

    private val viewModel: CameraViewModel by viewModels()

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
            if (result?.resultCode == Activity.RESULT_OK) {
                result.data?.let { intent ->
                    try {
                        intent.data?.let { uri ->
                            // ToDo 画像確認画面に遷移
                        }
                    } catch (e: IOException) {
                        Timber.e("画像取得エラー ${e.message}")
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityCameraBinding>(this, R.layout.activity_camera)
        binding.viewModel = viewModel

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        outputDirectory = getOutputDirectory()

        binding.cameraCaptureButton.setOnClickListener {
            viewModel.takePhoto(imageCapture, outputDirectory)
        }

        binding.imageAttachment.setOnClickListener { onImageAttachmentClick() }

        viewModel.imageUrl.observe(this) {
            // ToDo 画像確認画面に遷移
        }

        viewModel.errorResourceId.observe(this) { resourceId ->
            showToast(resourceId)
        }
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
                showToast(R.string.camera_permission_message)
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
                Timber.e("バインディング失敗 $e")
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

    private fun onImageAttachmentClick() {
        Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = IMAGE_TYPE
            startForResult.launch(this)
        }
    }
}
