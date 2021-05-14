package haivv.learning.savingmoney.ui.feature.authencation.register.container

import android.graphics.Color
import android.os.Bundle
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.MaterialSharedAxis
import haivv.learning.base.BaseFragment
import haivv.learning.savingmoney.R
import haivv.learning.savingmoney.common.BaseViewPagerAdapter
import haivv.learning.savingmoney.common.SavingMoneyApplication
import haivv.learning.savingmoney.databinding.RegistrationFragmentBinding

import haivv.learning.savingmoney.ui.feature.authencation.register.confirm.RegisterConfirmFragment
import haivv.learning.savingmoney.ui.feature.authencation.register.form.RegisterFormFragment
import javax.inject.Inject
import kotlin.math.abs

class RegistrationFragment : BaseFragment<RegistrationFragmentBinding, RegistrationContainerVM>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val translateX get() = viewBinding.pager.orientation == ViewPager2.ORIENTATION_VERTICAL
    private val translateY get() = viewBinding.pager.orientation == ViewPager2.ORIENTATION_HORIZONTAL

    private val mAnimator = ViewPager2.PageTransformer { page, position ->
        val absPos = abs(position)
        page.apply {
            translationY = if (translateY) absPos * 600f else 0f
            translationX = if (translateX) absPos * 450f else 0f
        }
    }

    private val viewModel by viewModels<RegistrationContainerVM> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }
        // This callback will only be called when MyFragment is at least Started.
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Handle the back button event
            onBackPressed()
        }
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.registration_fragment
    }

    override fun initView() {
        setUpTitle()
        initViewPager()
        initTabLayout()
    }

    private fun setUpTitle() {
        viewBinding.title = getString(R.string.title_registration)
    }

    private fun initTabLayout() {
        val titleTabs = mutableListOf<String>()
        titleTabs.add("Register form")
        titleTabs.add("Register confirm")
        viewBinding.tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF6200EE"))
        viewBinding.tabLayout.setTabTextColors(
            Color.parseColor("#000000"),
            Color.parseColor("#FF6200EE")
        )
        TabLayoutMediator(viewBinding.tabLayout, viewBinding.pager) { tab, position ->
            tab.text = titleTabs[position]
        }.attach()
    }

    private fun initViewPager() {
        val registerFormFragment = RegisterFormFragment()
        val registerConfirmFragment = RegisterConfirmFragment()
        val listFragment: MutableList<Fragment> = mutableListOf()
        listFragment.add(registerFormFragment)
        listFragment.add(registerConfirmFragment)
        val baseViewPagerAdapter =
            BaseViewPagerAdapter(
                this,
                listFragment
            )
        with(viewBinding.pager) {
            setPageTransformer(mAnimator)
            isUserInputEnabled = false
            adapter = baseViewPagerAdapter
        }

    }

    override fun initAction() {
        viewBinding.toolbar.imgBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun attachInjectFragment() {
        (requireActivity().application as SavingMoneyApplication).savingMoneyComponent.authenticationComponent()
            .create()
            .injectRegisterFragment(this)
    }

    override fun observeLiveData() {
        viewModel.goNextPage.observe(this, {enableNextPage ->
            if (enableNextPage) {
                viewBinding.pager.currentItem = viewBinding.pager.currentItem + 1
            }
        })
    }

    override fun onBackPressed() {
        val currentItem = viewBinding.pager.currentItem
        if (currentItem == 0) {
            super.onBackPressed()
        } else {
            viewBinding.pager.currentItem = currentItem - 1
        }
    }
}