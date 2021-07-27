package com.example.facialexpressionchampionship.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor() : ViewModel() {

    var theme = ObservableField<String>()
    get() {
        val list = listOf("怒り", "軽蔑", "嫌悪", "恐れ", "幸せ", "通常", "悲しみ", "驚き")
        return ObservableField(list.shuffled()[0])
    }
}
