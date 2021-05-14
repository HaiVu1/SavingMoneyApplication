package haivv.learning.domain.base

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor

abstract class BaseObservableUseCase<T, Params>(
    private val compositeDisposable: CompositeDisposable
) {
    abstract fun buildUseCaseObservable(params: Params): Observable<T>

     operator fun invoke(disposableObserver: DisposableObserver<T>, params: Params) {
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