package haivv.learning.savingmoney.di.sub_component

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import haivv.learning.data.feature.authenticate.AuthenticationRepoImp
import haivv.learning.data.feature.authenticate.AuthenticationRepository
import haivv.learning.savingmoney.di.module.ViewModelKey
import haivv.learning.savingmoney.ui.feature.authencation.register.container.RegistrationContainerVM
import haivv.learning.savingmoney.ui.feature.authencation.register.confirm.RegistrationConfirmVM
import haivv.learning.savingmoney.ui.feature.authencation.register.form.RegistrationFormVM

@Module
abstract class AuthenticationModule {
    @Binds
    @IntoMap
    @ViewModelKey(RegistrationContainerVM::class)
    abstract fun bindsMoviesViewModel(registrationContainerVM: RegistrationContainerVM): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(RegistrationConfirmVM::class)
    abstract fun bindsRegistrationConfirmVM(registrationConfirmVM: RegistrationConfirmVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationFormVM::class)
    abstract fun bindsRegistrationFormVM(registrationFormVM: RegistrationFormVM): ViewModel


    @Binds
    abstract fun bindAuthenticateRepo(authenticationRepo: AuthenticationRepoImp): AuthenticationRepository
}