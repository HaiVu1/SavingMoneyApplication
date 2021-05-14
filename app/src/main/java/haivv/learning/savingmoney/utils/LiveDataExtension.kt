package haivv.learning.savingmoney.utils

import androidx.annotation.NonNull
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun <T> LiveData<T>.emitObserve(@NonNull owner: LifecycleOwner, handleData: (t: T) -> Unit) {
    observe(owner, {
        handleData(it)
    })
}