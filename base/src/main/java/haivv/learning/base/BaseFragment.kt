package haivv.learning.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController

abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel> : Fragment() {
    protected lateinit var viewBinding: V
    private lateinit var viewModel: VM
    private var navController: NavController? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        attachInjectFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, getLayoutResourceId(), container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNavController()
        initView()
        observeLiveData()
        initAction()
    }

    @LayoutRes
    abstract fun getLayoutResourceId(): Int

    abstract fun initView()

    abstract fun attachInjectFragment()

    abstract fun observeLiveData()

    abstract fun initAction()

    private fun getNavController() {
        try {
            navController = findNavController()
        } catch (e: Exception) {

        }
    }

    private fun popBackStackSafety() {
        val thisDestinationName = this::class.java.name
        val currentDestination = navController?.currentDestination as? FragmentNavigator.Destination
        val currentDestinationName = currentDestination?.className

        if (thisDestinationName == currentDestinationName) {
            currentDestination.let {
                navController?.popBackStack(currentDestination.id, true)
            }
        }
    }

    protected fun popBackStack() {
        val currentDestination = navController?.currentDestination as? FragmentNavigator.Destination

        currentDestination?.let {
            navController?.popBackStack(currentDestination.id, true)
        }

    }

    protected fun popBackToSafety(desId: Int) {
        val currentDestination = navController?.currentDestination
        val currentDestinationId = currentDestination?.id

        if (desId != currentDestinationId) {
            navController?.popBackStack(desId, false)
        }
    }

    protected fun navigateSafety(action: Int) {
        if (navController?.currentDestination?.getAction(action) != null) {
            try {
                navController?.navigate(action)
            } catch (e: Exception) {

            }
        }
    }

    protected fun navigateSafety(direction: NavDirections) {
        val currentDestination = navController?.currentDestination?.id
        val destination = navController?.graph?.find { des ->
            des.getAction(direction.actionId) != null
        }

        if (destination != null && destination.id == currentDestination) {
            try {
                navController?.navigate(direction)
            } catch (e: Exception) {

            }
        }
    }

    protected open fun onBackPressed() {
        popBackStackSafety()
    }

    override fun onDestroyView() {
        viewBinding.unbind()
        super.onDestroyView()
    }
}