package com.fidato.shaadiassignment.main.data

import com.fidato.shaadiassignment.ShaadiAssignmentApp
import com.fidato.shaadiassignment.base.ShaadiAssignmenDb
import com.fidato.shaadiassignment.main.data.dao.MatchesDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class MainNetworkDataProvider(private val mainServies: MainServies){
    suspend fun getMatches(noOfResults: Int) = mainServies.getMatches(noOfResults)
}