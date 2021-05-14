package haivv.learning.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


abstract class BaseViewModel(vararg repository: BaseRepository) : ViewModel() {

    lateinit var compositeDisposable: CompositeDisposable

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}