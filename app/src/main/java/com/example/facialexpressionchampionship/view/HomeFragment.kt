package com.example.facialexpressionchampionship.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.databinding.FragmentHomeBinding
import com.example.facialexpressionchampionship.extension.showFragment
import com.example.facialexpressionchampionship.view.battle.BattleActivity
import com.example.facialexpressionchampionship.view.history.BattleHistoryFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.let {
            it.setTitle(R.string.app_name)
            it.setDisplayHomeAsUpEnabled(false)
        }

        binding.battle.setOnClickListener {
            val intent = Intent(requireActivity(), BattleActivity::class.java)
            startActivity(intent)
        }

        binding.battleHistory.setOnClickListener {
            BattleHistoryFragment().showFragment(
                parentFragmentManager,
                R.id.fragment_container,
                true
            )
        }
    }
}
