package haivv.learning.domain.authentication

import haivv.learning.data.feature.authenticate.AuthenticationRepository
import haivv.learning.data.local.entities.User
import haivv.learning.domain.base.BaseSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class CreateUserUC @Inject constructor(
    private val userRepository: AuthenticationRepository,
) : BaseSingleUseCase<Long, User>() {

    override fun buildUseCaseSingle(params: User): Single<Long> {
        return userRepository.createUser(params)
    }
}