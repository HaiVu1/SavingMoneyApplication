package haivv.learning.savingmoney.di.sub_component

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import haivv.learning.savingmoney.di.module.ViewModelKey
import haivv.learning.savingmoney.ui.feature.home.HomeViewModel

@Module
abstract class HomeModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindsHomeViewModel(homeViewModel: HomeViewModel): ViewModel
}