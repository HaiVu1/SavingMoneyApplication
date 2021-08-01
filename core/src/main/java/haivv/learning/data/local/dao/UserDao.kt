package haivv.learning.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import haivv.learning.data.local.base.BaseDao
import haivv.learning.data.local.entities.User
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface UserDao : BaseDao<User> {

    /**
     * Get User by id
     *
     * @param id ID User
     * @return LiveData<User> Observer User
     */
    @Query("SELECT * FROM User WHERE userId = :id")
    fun getUserById(id: String): Flowable<User>

    @Query("SELECT * FROM User WHERE email = :email AND password =:password")
    fun getUserByEmailAndPass(email: String, password: String): Single<User>
}