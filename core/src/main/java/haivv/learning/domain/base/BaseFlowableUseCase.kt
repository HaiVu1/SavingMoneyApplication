package haivv.learning.domain.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import haivv.learning.data.Result
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class BaseFlowableUseCase<T, Params> {
    lateinit var compositeDisposable: CompositeDisposable
    abstract fun buildUseCaseObservable(params: Params): Flowable<T>

    operator fun invoke(params: Params): LiveData<Result<T>> {
        val result = MutableLiveData<Result<T>>()
        result.value = Result.Loading
        val observer = this.buildUseCaseObservable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result.value = Result.Success(it)
            }, {
                result.value = Result.Error(it)
            })

        compositeDisposable.add(observer)
        return result
    }

    fun dispose() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

}