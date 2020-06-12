package com.fidato.shaadiassignment.main.data

import android.util.Log
import com.fidato.shaadiassignment.ShaadiAssignmentApp
import com.fidato.shaadiassignment.base.ShaadiAssignmenDb
import com.fidato.shaadiassignment.main.data.dao.MatchesDao
import com.fidato.shaadiassignment.model.MatchesModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MainRepository(private val mainNetworkDataProvider: MainNetworkDataProvider) :
    CoroutineScope {

    private val TAG = MainRepository::class.java.canonicalName

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var matchesDao: MatchesDao?

    init {
        val db = ShaadiAssignmenDb.getDatabase(ShaadiAssignmentApp.instance)
        matchesDao = db?.matchesDao()
    }

    suspend fun getMatches(noOfResults: Int) = mainNetworkDataProvider.getMatches(noOfResults)

    suspend fun getMatches() = matchesDao?.getAllMatches()

    suspend fun getMatchesByGender(gender: String) = matchesDao?.getMatchesByGender(gender)

    suspend fun getMatchesByAcceptanceState(acceptanceState: Int) = matchesDao?.getMatchesByAcceptanceState(acceptanceState)

    suspend fun storeMatch(matchModel: MatchesModel) {
        setMatchBG(matchModel)
    }

    suspend fun storeMatches(arylstMatchesModel: ArrayList<MatchesModel>) {
        for (matchesModel in arylstMatchesModel) {
            setMatchBG(matchesModel)
        }
    }

    private suspend fun setMatchBG(matchModel: MatchesModel) {
        withContext(Dispatchers.IO) {
            val addedId = matchesDao?.setMatch(matchModel)
            Log.d(TAG, "udpateMatchBG::addedId: $addedId")
        }
    }

    suspend fun updateMatch(matchModel: MatchesModel) {
        udpateMatchBG(matchModel)
    }

    private suspend fun udpateMatchBG(matchModel: MatchesModel) {
        withContext(Dispatchers.IO) {
            val updated = matchesDao?.updateMatch(matchModel)
            Log.d(TAG, "udpateMatchBG::$updated")
        }
    }

}