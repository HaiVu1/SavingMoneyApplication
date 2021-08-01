package haivv.learning.domain.authentication

import haivv.learning.data.feature.authenticate.AuthenticationRepository
import haivv.learning.data.local.entities.User
import haivv.learning.domain.base.BaseSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class LoginUC @Inject constructor(private val userRepository: AuthenticationRepository) :
    BaseSingleUseCase<User, User>() {
    override fun buildUseCaseSingle(params: User): Single<User> {
        return userRepository.getUserByEmail(params.email, params.password)
    }
}