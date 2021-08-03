package com.example.facialexpressionchampionship.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageConfirmationViewModel @Inject constructor() : ViewModel() {

    var theme = ObservableField<String>()

    var imageUrl = ObservableField<String>()
}
