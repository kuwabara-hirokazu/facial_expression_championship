package com.example.facialexpressionchampionship.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.facialexpressionchampionship.databinding.FragmentImageConfirmationBinding
import com.example.facialexpressionchampionship.viewmodel.ImageConfirmationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageConfirmationFragment : Fragment() {

    private val viewModel: ImageConfirmationViewModel by viewModels()
    private lateinit var binding: FragmentImageConfirmationBinding

    companion object {
        private const val THEME = "arg_theme"
        private const val URL = "arg_url"
        fun createInstance(theme: String?, imageUrl: String): Fragment {
            val fragment = ImageConfirmationFragment()
            val args = bundleOf(THEME to theme, URL to imageUrl)
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

        binding.viewModel = viewModel
        viewModel.theme.set(arguments?.getString(THEME))
        viewModel.imageUrl.set(checkNotNull(arguments?.getString(URL)))

        binding.retry.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.score.setOnClickListener {
            // 採点結果画面に遷移
        }
    }
}
