package haivv.learning.savingmoney.di.module

import dagger.Module
import haivv.learning.savingmoney.di.sub_component.AuthenticationComponent
import haivv.learning.savingmoney.di.sub_component.HomeComponent

@Module(
    subcomponents = [
        AuthenticationComponent::class,
        HomeComponent::class
    ]
)
object SubComponentsModule