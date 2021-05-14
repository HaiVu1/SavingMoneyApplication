package haivv.learning.di.authentication

import dagger.Subcomponent

@Subcomponent(
    modules = [
        AuthenticationDataModule::class
    ]
)
interface AuthenticationCoreComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthenticationCoreComponent
    }
}
