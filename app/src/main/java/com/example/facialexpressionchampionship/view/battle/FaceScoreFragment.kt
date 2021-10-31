package com.example.facialexpressionchampionship.view.battle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.databinding.FragmentFaceScoreBinding
import com.example.facialexpressionchampionship.extension.showFragment
import com.example.facialexpressionchampionship.extension.showToast
import com.example.facialexpressionchampionship.model.FaceScore
import com.example.facialexpressionchampionship.viewmodel.battle.BattleViewModel
import com.example.facialexpressionchampionship.viewmodel.battle.FaceScoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy

@AndroidEntryPoint
class FaceScoreFragment : Fragment() {

    private val battleViewModel: BattleViewModel by viewModels({ requireActivity() })
    private val viewModel: FaceScoreViewModel by viewModels()
    private lateinit var binding: FragmentFaceScoreBinding

    private val disposable = CompositeDisposable()

    companion object {
        private const val URL = "arg_url"
        private const val SCORE = "arg_score"
        fun createInstance(imageUrl: String?, score: FaceScore): Fragment {
            val fragment = FaceScoreFragment()
            val args = bundleOf(URL to imageUrl, SCORE to score)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFaceScoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.battleViewModel = battleViewModel
        binding.viewModel = viewModel

        (activity as AppCompatActivity).supportActionBar?.let {
            it.setTitle(R.string.scoring_result)
        }

        viewModel.imageUrl.set(checkNotNull(arguments?.getString(URL)))
        val score = arguments?.getSerializable(SCORE) as FaceScore
        viewModel.setScore(score, battleViewModel.themeType)
        viewModel.setBattleCount(battleViewModel.getSortedScoreList().size)

        binding.nextChallenger.setOnClickListener {
            val scoreData = viewModel.validateScore() ?: return@setOnClickListener
            battleViewModel.addScoreList(scoreData)
            CameraFragment().showFragment(parentFragmentManager, R.id.battle_layout, false)
        }

        binding.ranking.setOnClickListener {
            val scoreData = viewModel.validateScore() ?: return@setOnClickListener
            battleViewModel.addScoreList(scoreData)
            FaceScoreRankingFragment().showFragment(parentFragmentManager, R.id.battle_layout, false)
        }

        viewModel.conditionInvalid
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                requireContext().showToast(it)
            }
            .addTo(disposable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.clear()
    }
}
