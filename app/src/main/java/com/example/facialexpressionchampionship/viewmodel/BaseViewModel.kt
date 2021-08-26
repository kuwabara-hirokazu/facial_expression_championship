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
import retrofit2.HttpException
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    private val disposables = CompositeDisposable()
    val error: PublishSubject<Failure> = PublishSubject.create()

    protected fun <T : Any> Single<T>.execute(onSuccess: (T) -> Unit, retry: () -> Unit) {
        this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = onSuccess,
                onError = {
                    Timber.e(it.message)
                    error.onNext(Failure(it, it.toMessage(), retry))
                }
            )
            .addTo(disposables)
    }

    private fun Throwable.toMessage(): Int {
        return when (this) {
            is HttpException -> toMessage()
            is ArrayIndexOutOfBoundsException -> R.string.failed_facial_expression
            else -> R.string.error_message_default
        }
    }

    private fun HttpException.toMessage(): Int {
        return when (code()) {
            400 -> R.string.failed_detect_image
            408 -> R.string.time_out_error
            else -> R.string.uncertain_error
        }
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}
