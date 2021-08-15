package com.example.facialexpressionchampionship.viewmodel

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.model.Emotion
import io.reactivex.rxjava3.subjects.PublishSubject

class FaceScoreViewModel : BaseViewModel() {
    var imageUrl = ObservableField<String>()

    var challenger = ObservableField<String>()

    var anger = ObservableField<String>()

    var contempt = ObservableField<String>()

    var disgust = ObservableField<String>()

    var fear = ObservableField<String>()

    var happiness = ObservableField<String>()

    var neutral = ObservableField<String>()

    var sadness = ObservableField<String>()

    var surprise = ObservableField<String>()

    val isContinue: PublishSubject<Boolean> = PublishSubject.create()

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

    fun saveScore() {

    }
}
