package com.example.facialexpressionchampionship.extension

import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
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

    @BindingAdapter("showProgress")
    @JvmStatic
    fun ProgressBar.showProgressBar(isShow: Boolean) {
        this.isVisible = isShow
    }
}
