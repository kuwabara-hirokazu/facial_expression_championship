package com.example.facialexpressionchampionship.view.battle

import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.databinding.ItemFaceScoreBinding
import com.example.facialexpressionchampionship.model.ScoreData
import com.xwray.groupie.databinding.BindableItem

class FaceScoreRankingItem(
    private val ranking: Int,
    private val scoreData: ScoreData,
) : BindableItem<ItemFaceScoreBinding>() {

    override fun bind(viewBinding: ItemFaceScoreBinding, position: Int) {
        viewBinding.scoreData = scoreData
        viewBinding.ranking = ranking
    }

    override fun getLayout() = R.layout.item_face_score
}
