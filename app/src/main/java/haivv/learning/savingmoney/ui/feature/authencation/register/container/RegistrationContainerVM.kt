package haivv.learning.savingmoney.ui.feature.authencation.register.container

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import haivv.learning.base.BaseViewModel
import haivv.learning.data.Result
import haivv.learning.data.local.entities.User
import haivv.learning.domain.authentication.CreateUserUC
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 *  Share data
 */
open class RegistrationContainerVM @Inject constructor(var createUserUC: CreateUserUC) :
    BaseViewModel() {
    var registerValue: MediatorLiveData<Result<Long>> = MediatorLiveData()

    private val _goNextPage: MutableLiveData<Boolean> = MutableLiveData()
    val goNextPage: LiveData<Boolean>
        get() = _goNextPage

    private val _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User>
        get() = _user


    init {
        compositeDisposable = CompositeDisposable()
        createUserUC.compositeDisposable = compositeDisposable
    }

    /**
     *  Register user
     *
     *  @param user User need register
     */
    fun register(user: User) {
        _user.value = user
        val registerResult = createUserUC(user)
        registerValue.addSource(registerResult) { result ->
            if (result != Result.Loading) {
                registerValue.removeSource(registerResult)
                registerValue.addSource(registerResult) { newResult ->
                    registerValue.value = newResult
                }
            } else {
                registerValue.value = result
            }
        }
    }

    /**
     *  Change page form user information to confirm user information
     */
    fun goNextPage() {
        _goNextPage.value = true
    }

    /**
     *  Setter user
     */
    fun setUser(user: User) {
        _user.value = user
    }
}