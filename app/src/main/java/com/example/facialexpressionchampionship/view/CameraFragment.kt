package com.example.facialexpressionchampionship.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.databinding.FragmentCameraBinding
import com.example.facialexpressionchampionship.extension.*
import com.example.facialexpressionchampionship.viewmodel.BattleViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class CameraFragment : Fragment() {

    private var imageCapture: ImageCapture? = null

    @Inject lateinit var outputDirectory: File

    private val battleViewModel: BattleViewModel by viewModels({ requireActivity() })
    private lateinit var binding: FragmentCameraBinding

    private val startForResult =
        registerForActivityResult(
            success = {
                ImageConfirmationFragment.createInstance(it)
                    .showFragment(parentFragmentManager, R.id.battle_layout, true)
            },
            error = { requireContext().showToast(it) }
        )

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
        (activity as AppCompatActivity).supportActionBar?.let {
            it.setTitle(R.string.app_name)
        }

        binding.imageAttachment.setOnClickListener { openLibrary(startForResult) }

        setUpCamera()
    }

    private fun setUpCamera() {
        imageCapture = ImageCapture.Builder().build()

        binding.cameraCaptureButton.setOnClickListener {
            imageCapture?.takePicture(outputDirectory,
                success = {
                    ImageConfirmationFragment.createInstance(it)
                        .showFragment(parentFragmentManager, R.id.battle_layout, true)
                },
                error = { requireContext().showToast(it.message) }
            )
        }

        startCamera()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            // プレビュー設定
            val preview = Preview.Builder()
                .build()

            imageCapture = ImageCapture.Builder().build()

            // デフォルトを内カメラに設定
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            try {
                cameraProvider.unbindAll()
                // ライフサイクルにカメラをバインディング
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)

                // プレビューのユースケースをpreviewViewに接続
                preview.setSurfaceProvider(binding.previewView.surfaceProvider)

            } catch (e: Exception) {
                Timber.e("バインディング失敗 $e")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }
}
