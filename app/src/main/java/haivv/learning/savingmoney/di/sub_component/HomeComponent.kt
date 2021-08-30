package haivv.learning.savingmoney.di.sub_component

import dagger.Subcomponent
import haivv.learning.savingmoney.ui.feature.home.HomeFragment

@Subcomponent(
    modules = [
        HomeModule::class
    ]
)
interface HomeComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): HomeComponent
    }

    fun injectHomeFragment(homeFragment: HomeFragment)
}