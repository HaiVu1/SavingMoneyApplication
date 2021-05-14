package haivv.learning.savingmoney.di.sub_component

import dagger.Subcomponent
import haivv.learning.savingmoney.ui.feature.authencation.login.LoginFragment
import haivv.learning.savingmoney.ui.feature.authencation.register.confirm.RegisterConfirmFragment
import haivv.learning.savingmoney.ui.feature.authencation.register.container.RegistrationFragment
import haivv.learning.savingmoney.ui.feature.authencation.register.form.RegisterFormFragment

@Subcomponent(
    modules = [
        AuthenticationModule::class
    ]
)
interface AuthenticationComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthenticationComponent
    }

    fun injectLoginFragment(loginFragment: LoginFragment)

    fun injectRegisterFragment(registerFragment: RegistrationFragment)
}