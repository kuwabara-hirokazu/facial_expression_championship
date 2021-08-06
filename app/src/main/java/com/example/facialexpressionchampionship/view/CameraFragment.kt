package com.example.facialexpressionchampionship.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.databinding.FragmentCameraBinding
import com.example.facialexpressionchampionship.extension.showFragment
import com.example.facialexpressionchampionship.extension.showToast
import com.example.facialexpressionchampionship.viewmodel.BattleViewModel
import com.example.facialexpressionchampionship.viewmodel.CameraViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_camera.*
import timber.log.Timber
import java.io.File
import java.io.IOException

@AndroidEntryPoint
class CameraFragment : Fragment() {

    companion object {
        private const val IMAGE_TYPE = "image/*"
    }

    private var imageCapture: ImageCapture? = null

    private lateinit var outputDirectory: File

    private val battleViewModel: BattleViewModel by viewModels({requireActivity()})
    private val viewModel: CameraViewModel by viewModels()
    private lateinit var binding: FragmentCameraBinding

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
            if (result?.resultCode == Activity.RESULT_OK) {
                result.data?.let { intent ->
                    try {
                        intent.data?.let { uri ->
                            ImageConfirmationFragment.createInstance(uri.toString())
                                .showFragment(parentFragmentManager, R.id.battle_layout, true)
                        }
                    } catch (e: IOException) {
                        Timber.e("画像取得エラー ${e.message}")
                    }
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.battleViewModel = battleViewModel
        binding.viewModel = viewModel

        outputDirectory = getOutputDirectory()

        binding.cameraCaptureButton.setOnClickListener {
            viewModel.takePhoto(imageCapture, outputDirectory)
        }

        binding.imageAttachment.setOnClickListener { onImageAttachmentClick() }

        viewModel.imageUrl.observe(viewLifecycleOwner) {
            ImageConfirmationFragment.createInstance(it)
                .showFragment(parentFragmentManager, R.id.battle_layout, true)
        }

        viewModel.errorResourceId.observe(viewLifecycleOwner) { resourceId ->
            requireContext().showToast(resourceId)
        }

        startCamera()
    }


    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

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
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun getOutputDirectory(): File {
        // ストレージ/Android/media にディレクトリ作成
        val mediaDir = requireContext().externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else requireContext().filesDir
    }

    private fun onImageAttachmentClick() {
        Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = IMAGE_TYPE
            startForResult.launch(this)
        }
    }
}
