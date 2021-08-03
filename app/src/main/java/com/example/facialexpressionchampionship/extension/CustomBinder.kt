package com.example.facialexpressionchampionship.extension

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object CustomBinder {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun ImageView.loadImageUrl(url: String?) {
        Glide.with(context)
            .load(url)
            .into(this)
    }
}
