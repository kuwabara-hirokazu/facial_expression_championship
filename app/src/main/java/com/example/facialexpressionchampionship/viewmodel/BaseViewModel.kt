package com.example.facialexpressionchampionship.viewmodel

import androidx.lifecycle.ViewModel
import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.model.Failure
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    private val disposables = CompositeDisposable()
    val error: PublishSubject<Failure> = PublishSubject.create()

    protected fun <T : Any> Single<T>.execute(onSuccess: (T) -> Unit) {
        this.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = onSuccess,
                onError = {
                    Timber.e(it.message)
                    error.onNext(Failure(it, it.toMessage(), {}))
                }
            )
            .addTo(disposables)
    }

    private fun Throwable.toMessage(): Int {
        return R.string.failed_save_image
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}
