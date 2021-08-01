package haivv.learning.data.feature.authenticate

import haivv.learning.data.local.entities.User
import io.reactivex.Flowable
import io.reactivex.Single

interface AuthenticationRepository {

    fun createUser(user: User): Single<Long>

    fun getUserById(id: String): Flowable<User>

    fun updateUser(user: User): Single<Int>

    fun deleteUser(user: User): Single<Int>

    fun getUserByEmail(email: String, password: String): Single<User>
}