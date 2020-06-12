package com.fidato.shaadiassignment.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fidato.shaadiassignment.main.data.MainNetworkDataProvider
import com.fidato.shaadiassignment.main.data.MainRepository
import com.fidato.shaadiassignment.main.viewmodel.MainViewModel

class ViewModelFactory<T>(private val dataProvider: T, private val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                application,
                MainRepository(dataProvider as MainNetworkDataProvider)
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}

