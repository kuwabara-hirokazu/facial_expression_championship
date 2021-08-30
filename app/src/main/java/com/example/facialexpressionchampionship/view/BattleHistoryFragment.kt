package com.example.facialexpressionchampionship.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.facialexpressionchampionship.databinding.FragmentBattleHistoryBinding
import com.example.facialexpressionchampionship.viewmodel.BattleHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BattleHistoryFragment : Fragment() {

    private val viewModel: BattleHistoryViewModel by viewModels()
    private lateinit var binding: FragmentBattleHistoryBinding

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
    }
}
