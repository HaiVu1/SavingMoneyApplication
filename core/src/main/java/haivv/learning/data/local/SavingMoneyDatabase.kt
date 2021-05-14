package haivv.learning.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import haivv.learning.data.local.dao.UserDao
import haivv.learning.data.local.entities.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class SavingMoneyDatabase : RoomDatabase(){

    abstract fun userDao(): UserDao
}