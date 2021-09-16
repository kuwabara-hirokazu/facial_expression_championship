package com.example.facialexpressionchampionship.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.facialexpressionchampionship.databinding.FragmentBattleHistoryDetailBinding
import com.example.facialexpressionchampionship.extension.showToast
import com.example.facialexpressionchampionship.model.BattleHistoryBusinessModel
import com.example.facialexpressionchampionship.viewmodel.BattleHistoryDetailViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy

@AndroidEntryPoint
class BattleHistoryDetailFragment : Fragment() {

    private val viewModel: BattleHistoryDetailViewModel by viewModels()
    private lateinit var binding: FragmentBattleHistoryDetailBinding

    private val disposable = CompositeDisposable()

    companion object {
        private const val HISTORY = "arg_history"
        fun createInstance(history: BattleHistoryBusinessModel): Fragment {
            val fragment = BattleHistoryDetailFragment()
            val args = bundleOf(HISTORY to history)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBattleHistoryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        val history = arguments?.getSerializable(HISTORY) as BattleHistoryBusinessModel
        viewModel.history.set(history)

        adapter.update(viewModel.getChallengerList().map { BattleHistoryDetailItem(it) })

        binding.delete.setOnClickListener {
            viewModel.deleteHistory()
        }

        viewModel.deleted
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                requireContext().showToast(it)
                parentFragmentManager.popBackStack()
            }
            .addTo(disposable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.clear()
    }
}
