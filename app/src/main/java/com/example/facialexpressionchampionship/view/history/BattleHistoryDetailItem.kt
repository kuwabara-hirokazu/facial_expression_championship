package com.example.facialexpressionchampionship.view.history

import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.databinding.ItemBattleHistoryDetailBinding
import com.example.facialexpressionchampionship.model.Challenger
import com.xwray.groupie.databinding.BindableItem

class BattleHistoryDetailItem(
    private val challenger: Challenger?
) : BindableItem<ItemBattleHistoryDetailBinding>() {

    override fun bind(viewBinding: ItemBattleHistoryDetailBinding, position: Int) {
        viewBinding.challenger = challenger
    }

    override fun getLayout(): Int {
        return R.layout.item_battle_history_detail
    }
}
