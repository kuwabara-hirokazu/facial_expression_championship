package com.example.facialexpressionchampionship.view

import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.databinding.ItemBattleHistoryBinding
import com.example.facialexpressionchampionship.model.BattleHistoryBusinessModel
import com.xwray.groupie.databinding.BindableItem

class BattleHistoryItem(
    private val history: BattleHistoryBusinessModel
) : BindableItem<ItemBattleHistoryBinding>() {

    override fun bind(viewBinding: ItemBattleHistoryBinding, position: Int) {
        viewBinding.history = history
    }

    override fun getLayout() = R.layout.item_battle_history

}
