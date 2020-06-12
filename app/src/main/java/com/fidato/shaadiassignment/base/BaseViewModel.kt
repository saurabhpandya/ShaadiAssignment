package com.fidato.shaadiassignment.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.fidato.shaadiassignment.ShaadiAssignmentApp
import com.fidato.shaadiassignment.main.data.MainNetworkDataProvider
import com.fidato.shaadiassignment.main.viewmodel.MainViewModel
import com.fidato.shaadiassignment.networking.RetrofitClient

open class BaseViewModel(application: Application) : AndroidViewModel(application) {




}