package com.example.facialexpressionchampionship.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.databinding.FragmentFaceScoreBinding
import com.example.facialexpressionchampionship.extension.showError
import com.example.facialexpressionchampionship.extension.showFragment
import com.example.facialexpressionchampionship.model.Emotion
import com.example.facialexpressionchampionship.viewmodel.BattleViewModel
import com.example.facialexpressionchampionship.viewmodel.FaceScoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy

@AndroidEntryPoint
class FaceScoreFragment : Fragment() {

    private val battleViewModel: BattleViewModel by viewModels({requireActivity()})
    private val viewModel: FaceScoreViewModel by viewModels()
    private lateinit var binding: FragmentFaceScoreBinding

    private val disposable = CompositeDisposable()

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

        binding.nextChallenger.setOnClickListener {
            viewModel.saveScore()
        }

        binding.ranking.setOnClickListener {
            viewModel.saveScore()
        }

        viewModel.isContinue
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                when(it) {
                    true -> CameraFragment().showFragment(parentFragmentManager, R.id.battle_layout, false)
                    false -> {} // ToDo 順位発表画面に遷移
                }
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
