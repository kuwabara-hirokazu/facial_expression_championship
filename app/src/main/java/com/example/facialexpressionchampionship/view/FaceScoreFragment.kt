package com.example.facialexpressionchampionship.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.facialexpressionchampionship.databinding.FragmentFaceScoreBinding
import com.example.facialexpressionchampionship.model.Emotion
import com.example.facialexpressionchampionship.viewmodel.BattleViewModel
import com.example.facialexpressionchampionship.viewmodel.FaceScoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FaceScoreFragment : Fragment() {

    private val battleViewModel: BattleViewModel by viewModels({requireActivity()})
    private val viewModel: FaceScoreViewModel by viewModels()
    private lateinit var binding: FragmentFaceScoreBinding

    companion object {
        private const val URL = "arg_url"
        private const val EMOTION = "arg_emotion"
        fun createInstance(imageUrl: String?, emotion: Emotion) : Fragment {
            val fragment = FaceScoreFragment()
            val args = bundleOf(URL to imageUrl, EMOTION to emotion)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFaceScoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.battleViewModel = battleViewModel
        binding.viewModel = viewModel

        viewModel.imageUrl.set(checkNotNull(arguments?.getString(URL)))
        val emotion = arguments?.getSerializable(EMOTION) as Emotion
        viewModel.setEmotion(emotion)
    }
}
