package haivv.learning.savingmoney.ui.feature.authencation.login

import androidx.lifecycle.MediatorLiveData
import haivv.learning.base.BaseViewModel
import haivv.learning.data.Result
import haivv.learning.data.local.entities.User
import haivv.learning.domain.authentication.LoginUC
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

open class LoginViewModel @Inject constructor(var loginUC: LoginUC) : BaseViewModel() {
    val loginValue: MediatorLiveData<Result<User?>> = MediatorLiveData()

    init {
        compositeDisposable = CompositeDisposable()
        loginUC.compositeDisposable = compositeDisposable
    }

    fun login(user: User) {
        val loginResult = loginUC(user)
        loginValue.addSource(loginResult) { result ->
            if (result != Result.Loading) {
                loginValue.removeSource(loginResult)
                loginValue.addSource(loginResult) { newResult ->
                    loginValue.value = newResult
                }
            } else {
                loginValue.value = result
            }
        }
    }
}