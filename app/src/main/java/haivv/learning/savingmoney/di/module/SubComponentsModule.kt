package haivv.learning.savingmoney.di.module

import dagger.Module
import haivv.learning.savingmoney.di.sub_component.AuthenticationComponent

@Module(
    subcomponents = [
        AuthenticationComponent::class
    ]
)
object SubComponentsModule