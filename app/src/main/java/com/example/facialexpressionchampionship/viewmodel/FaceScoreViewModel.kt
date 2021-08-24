package com.example.facialexpressionchampionship.viewmodel

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.data.ScoreCacheSource
import com.example.facialexpressionchampionship.model.Emotion
import com.example.facialexpressionchampionship.model.ScoreCache
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class FaceScoreViewModel @Inject constructor(
    private val cacheRepository: ScoreCacheSource
): BaseViewModel() {
    var imageUrl = ObservableField<String>()

    var challenger = ObservableField<String>()

    private lateinit var emotion: Emotion

    var anger = ObservableField<String>()

    var contempt = ObservableField<String>()

    var disgust = ObservableField<String>()

    var fear = ObservableField<String>()

    var happiness = ObservableField<String>()

    var neutral = ObservableField<String>()

    var sadness = ObservableField<String>()

    var surprise = ObservableField<String>()

    val isContinue: PublishSubject<Boolean> = PublishSubject.create()

    val blankName: PublishSubject<Int> = PublishSubject.create()

    fun setEmotion(emotion: Emotion) {
        this.emotion = emotion
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
        val name = challenger.get()?.let { it }
        if (name == null ) {
            blankName.onNext(R.string.enter_name)
            return
        }

        val score = imageUrl.get()?.let { ScoreCache(name, emotion, it) }
        if (score != null) {
            cacheRepository.addScoreList(score)
            isContinue.onNext(true)
        }
    }
}
