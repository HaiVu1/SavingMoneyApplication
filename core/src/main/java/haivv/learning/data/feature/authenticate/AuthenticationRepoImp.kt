package haivv.learning.data.feature.authenticate

import haivv.learning.data.local.dao.UserDao
import haivv.learning.data.local.entities.User
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class AuthenticationRepoImp @Inject constructor(
   private val userDao: UserDao
) : AuthenticationRepository {

    override fun createUser(user: User): Single<Long> {
        return userDao.insert(user)
    }

    override fun getUserById(id: String): Flowable<User> {
        return userDao.getUserById(id)
    }

    override fun updateUser(user: User): Single<Int> {
        return userDao.update(user)
    }

    override fun deleteUser(user: User): Single<Int> {
        return userDao.delete(user)
    }
}