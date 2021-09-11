package com.example.facialexpressionchampionship.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.facialexpressionchampionship.databinding.FragmentImageConfirmationBinding
import com.example.facialexpressionchampionship.extension.showError
import com.example.facialexpressionchampionship.extension.toByteArray
import com.example.facialexpressionchampionship.viewmodel.BattleViewModel
import com.example.facialexpressionchampionship.viewmodel.ImageConfirmationViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@AndroidEntryPoint
class ImageConfirmationFragment : Fragment() {

    private val battleViewModel: BattleViewModel by viewModels({requireActivity()})
    private val viewModel: ImageConfirmationViewModel by viewModels()
    private lateinit var binding: FragmentImageConfirmationBinding

    private val disposable = CompositeDisposable()

    companion object {
        private const val URL = "arg_url"
        private const val MEDEA_TYPE = "application/octet-stream"
        fun createInstance(imageUrl: String): Fragment {
            val fragment = ImageConfirmationFragment()
            val args = bundleOf(URL to imageUrl)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.battleViewModel = battleViewModel
        binding.viewModel = viewModel

        viewModel.imageUrl.set(checkNotNull(arguments?.getString(URL)))

        binding.retry.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.score.setOnClickListener {
            val byte = File(viewModel.imageUrl.get()).toByteArray()
            byte ?: return@setOnClickListener

            val requestBody =
                byte.toRequestBody(MEDEA_TYPE.toMediaTypeOrNull(), 0, byte.size)

            viewModel.detectFace(requestBody)
        }

        viewModel.score
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                battleViewModel.setScore(it)
                // 画面遷移
            }
            .addTo(disposable)

        viewModel.error
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { binding.root.showError(it) }
            .addTo(disposable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.clear()
    }
}
