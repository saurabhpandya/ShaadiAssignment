package com.fidato.shaadiassignment.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.fidato.shaadiassignment.ShaadiAssignmentApp
import com.fidato.shaadiassignment.main.data.MainNetworkDataProvider
import com.fidato.shaadiassignment.main.viewmodel.MainViewModel
import com.fidato.shaadiassignment.networking.RetrofitClient

open class BaseFragment : Fragment() {
    fun <T> setupViewModel(
        viewModelStoreOwner: ViewModelStoreOwner,
        viewModel: Class<T>
    ): ViewModel? {

        var vm: ViewModel? = null
        if (viewModel.isAssignableFrom(MainViewModel::class.java))
            vm = ViewModelProvider(
                viewModelStoreOwner,
                ViewModelFactory(
                    MainNetworkDataProvider(RetrofitClient.MAIN_SERVICE),
                    ShaadiAssignmentApp.instance
                )
            ).get(MainViewModel::class.java)
        return vm
    }
}