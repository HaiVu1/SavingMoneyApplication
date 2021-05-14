package haivv.learning.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import haivv.learning.data.local.dao.UserDao
import haivv.learning.di.authentication.AuthenticationCoreComponent
import javax.inject.Qualifier
import javax.inject.Singleton
import haivv.learning.data.local.SavingMoneyDatabase as SavingMoneyDatabase

@Module(
    subcomponents = [
        AuthenticationCoreComponent::class
    ]
)
object  CoreModule {
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class AuthenticationLocalDataSource

    @JvmStatic
    @Singleton
    @Provides
    fun provideDataBase(context: Context): SavingMoneyDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            SavingMoneyDatabase::class.java,
            "SavingMoney.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideUserDao( savingMoneyDatabase: SavingMoneyDatabase): UserDao{
        return savingMoneyDatabase.userDao()
    }
}