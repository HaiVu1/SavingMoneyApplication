package haivv.learning.domain.base

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import java.util.concurrent.Executor

abstract class BaseFlowableUseCase<T, Params>(
    private val compositeDisposable: CompositeDisposable
) {
    abstract fun buildUseCaseObservable(params: Params): Flowable<T>

     operator fun invoke(disposableObserver: DisposableSubscriber<T>, params: Params) {
        val observer = this.buildUseCaseObservable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        compositeDisposable.add(observer.subscribeWith(disposableObserver))
    }

    fun dispose() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

}