package com.example.facialexpressionchampionship.viewmodel

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.model.Emotion

class FaceScoreViewModel : BaseViewModel() {
    var imageUrl = ObservableField<String>()

    var anger = ObservableField<String>()

    var contempt = ObservableField<String>()

    var disgust = ObservableField<String>()

    var fear = ObservableField<String>()

    var happiness = ObservableField<String>()

    var neutral = ObservableField<String>()

    var sadness = ObservableField<String>()

    var surprise = ObservableField<String>()

    fun setEmotion(emotion: Emotion) {
        anger.set(emotion.anger)
        contempt.set(emotion.contempt)
        disgust.set(emotion.disgust)
        fear.set(emotion.fear)
        happiness.set(emotion.happiness)
        neutral.set(emotion.neutral)
        sadness.set(emotion.sadness)
        surprise.set(emotion.surprise)
    }
}
