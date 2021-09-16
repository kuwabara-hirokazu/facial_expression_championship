package com.example.facialexpressionchampionship.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.databinding.FragmentBattleHistoryBinding
import com.example.facialexpressionchampionship.extension.showFragment
import com.example.facialexpressionchampionship.viewmodel.BattleHistoryViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy

@AndroidEntryPoint
class BattleHistoryFragment : Fragment() {

    private val viewModel: BattleHistoryViewModel by viewModels()
    private lateinit var binding: FragmentBattleHistoryBinding

    private val disposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBattleHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        (activity as AppCompatActivity).supportActionBar?.let {
            it.setTitle(R.string.battle_history)
            it.setDisplayHomeAsUpEnabled(true)
        }
        setHasOptionsMenu(true)

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

        viewModel.historyList
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { historyList ->
                adapter.update(historyList.map { history ->
                    BattleHistoryItem(history) {
                        BattleHistoryDetailFragment.createInstance(history).showFragment(
                            parentFragmentManager, R.id.fragment_container, true
                        )
                    }
                })
            }
            .addTo(disposable)

        viewModel.loadHistory()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                parentFragmentManager.popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.clear()
    }
}
