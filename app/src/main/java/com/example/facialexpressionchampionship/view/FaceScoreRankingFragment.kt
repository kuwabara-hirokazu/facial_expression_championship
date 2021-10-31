package com.example.facialexpressionchampionship.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.facialexpressionchampionship.databinding.FragmentFaceScoreRankingBinding
import com.example.facialexpressionchampionship.extension.showToast
import com.example.facialexpressionchampionship.viewmodel.BattleViewModel
import com.example.facialexpressionchampionship.viewmodel.FaceScoreRankingViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy

@AndroidEntryPoint
class FaceScoreRankingFragment : Fragment() {

    private val battleViewModel: BattleViewModel by viewModels({ requireActivity() })
    private val viewModel: FaceScoreRankingViewModel by viewModels()
    private lateinit var binding: FragmentFaceScoreRankingBinding

    private val disposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFaceScoreRankingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.battleViewModel = battleViewModel
        binding.viewModel = viewModel

        val adapter = GroupAdapter<GroupieViewHolder>()
        binding.recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            this.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
        }
        adapter.update(battleViewModel.getSortedScoreList().mapIndexed { index, scoreData ->
            FaceScoreRankingItem(index + 1, scoreData)
        })

        binding.save.setOnClickListener {
            viewModel.saveRanking()
        }

        viewModel.inValid
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
