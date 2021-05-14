package haivv.learning.savingmoney.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import haivv.learning.di.CoreModule
import haivv.learning.savingmoney.di.module.AppModule
import haivv.learning.savingmoney.di.module.AppModuleBinds
import haivv.learning.savingmoney.di.module.SubComponentsModule
import haivv.learning.savingmoney.di.module.ViewModelBuilderModule
import haivv.learning.savingmoney.di.sub_component.AuthenticationComponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AppModuleBinds::class,
        SubComponentsModule::class,
        ViewModelBuilderModule::class,
        CoreModule::class
    ]
)
interface SavingMoneyComponent {

    @Component.Factory
    interface SavingMoneyFactory {
        fun create(@BindsInstance applicationContext: Context): SavingMoneyComponent
    }

    fun authenticationComponent() :AuthenticationComponent.Factory
}