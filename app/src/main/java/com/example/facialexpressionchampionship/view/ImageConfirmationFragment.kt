package com.example.facialexpressionchampionship.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.facialexpressionchampionship.databinding.FragmentImageConfirmationBinding
import com.example.facialexpressionchampionship.viewmodel.BattleViewModel
import com.example.facialexpressionchampionship.viewmodel.ImageConfirmationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageConfirmationFragment : Fragment() {

    private val battleViewModel: BattleViewModel by viewModels({requireActivity()})
    private val viewModel: ImageConfirmationViewModel by viewModels()
    private lateinit var binding: FragmentImageConfirmationBinding

    companion object {
        private const val URL = "arg_url"
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
            val url = mutableMapOf("url" to "https://d3bhdfps5qyllw.cloudfront.net/org/67/67bce941606fcba0f482059692984a64_1080x1337_h.jpg")
            viewModel.detectFace(url)
        }
    }
}
