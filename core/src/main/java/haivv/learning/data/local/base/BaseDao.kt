package haivv.learning.data.local.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import io.reactivex.Single


/**
 *  Class CRUD object in room database
 *
 *  @param T object.
 */
interface BaseDao<T> {

    /**
     * Insert an object in the database.
     *
     * @param obj the object to be inserted.
     * @return onSuccess onSuccess number row affected by insert.
     */
    @Insert
    fun insert(obj: T): Single<Long>

    /**
     * Insert an array of objects in the database.
     *
     * @param obj the objects to be inserted.
     * @return onSuccess list number row affected by insert.
     */
    @Insert
    fun insert(vararg obj: T): Single<List<Long>>

    /**
     * Update an object from the database.
     *
     * @param obj the object to be updated
     * @return onSuccess onSuccess number row affected by update.
     */
    @Update
    fun update(obj: T): Single<Int>

    /**
     * Delete an object from the database
     *
     * @param obj the object to be deleted
     * @return onSuccess onSuccess number row affected by delete.
     */
    @Delete
    fun delete(obj: T): Single<Int>
}