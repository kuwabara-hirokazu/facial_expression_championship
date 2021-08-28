package com.example.facialexpressionchampionship.view

import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.databinding.ItemFaceScoreBinding
import com.example.facialexpressionchampionship.model.ScoreCache
import com.xwray.groupie.databinding.BindableItem

class FaceScoreRankingItem(
    private val scoreCache: ScoreCache
) : BindableItem<ItemFaceScoreBinding>() {

    override fun bind(viewBinding: ItemFaceScoreBinding, position: Int) {
        viewBinding.scoreCache = scoreCache
    }

    override fun getLayout() = R.layout.item_face_score

}
