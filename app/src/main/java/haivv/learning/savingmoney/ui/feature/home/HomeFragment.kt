package haivv.learning.savingmoney.ui.feature.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import haivv.learning.base.BaseFragment
import haivv.learning.base.BaseViewModel
import haivv.learning.savingmoney.R
import haivv.learning.savingmoney.common.SavingMoneyApplication
import haivv.learning.savingmoney.databinding.HomeFragmentBinding
import javax.inject.Inject

class HomeFragment : BaseFragment<HomeFragmentBinding, BaseViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val loginViewModel by viewModels<HomeViewModel> { viewModelFactory }
    override fun getLayoutResourceId(): Int {
        return R.layout.home_fragment
    }

    override fun initView() {

    }

    override fun attachInjectFragment() {
        (requireActivity().application as SavingMoneyApplication).savingMoneyComponent.homeComponent()
            .create()
            .injectHomeFragment(this)
    }

    override fun observeLiveData() {

    }

    override fun initAction() {

    }
}