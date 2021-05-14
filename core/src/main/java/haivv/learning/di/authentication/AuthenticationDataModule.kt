package haivv.learning.di.authentication

import dagger.Binds
import dagger.Module
import haivv.learning.data.feature.authenticate.AuthenticationRepository
import haivv.learning.data.feature.authenticate.AuthenticationRepoImp

@Module
abstract class AuthenticationDataModule {

    @Binds
    abstract fun bindAuthenticateRepo(authenticationRepo: AuthenticationRepoImp): AuthenticationRepository
}