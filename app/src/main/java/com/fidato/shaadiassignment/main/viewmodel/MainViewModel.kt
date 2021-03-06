package com.fidato.shaadiassignment.main.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.fidato.shaadiassignment.base.BaseViewModel
import com.fidato.shaadiassignment.main.data.MainRepository
import com.fidato.shaadiassignment.model.Matches
import com.fidato.shaadiassignment.model.MatchesModel
import com.fidato.shaadiassignment.utility.AcceptanceGroup
import com.fidato.shaadiassignment.utility.GenderGroup
import com.fidato.shaadiassignment.utility.Resource
import com.fidato.shaadiassignment.utility.Utility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application, private val mainRepository: MainRepository) :
    BaseViewModel(application) {
    private val TAG = MainViewModel::class.java.canonicalName

    fun getMatches() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val matchesModel = mainRepository.getMatches(10)
            if (!matchesModel.results.isNullOrEmpty()) {
                prepareDataForDB(matchesModel.results!!).also { arylstMatchesModel ->
                    mainRepository.storeMatches(arylstMatchesModel)
                    emit(Resource.success(true))
                }
            } else {
                emit(Resource.error(null, "Empty list fetched"))
            }

        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.error(null, e.message ?: "Error Occured"))
        }
    }

    var matchesList = ArrayList<MatchesModel>()

    private fun prepareDataForDB(arylstMatches: ArrayList<Matches>): ArrayList<MatchesModel> {
        val arylstMatchesModel = ArrayList<MatchesModel>()
        for (match in arylstMatches) {
            var matchesModel = MatchesModel(email = match.email ?: "")
            matchesModel.gender = match.gender ?: ""
            matchesModel.title = match.name?.title ?: ""
            matchesModel.firstName = match.name?.first ?: ""
            matchesModel.lastName = match.name?.last ?: ""
            matchesModel.streetNumber = (match.location?.street?.number ?: 0).toString()
            matchesModel.streetName = match.location?.street?.name ?: ""
            matchesModel.city = match.location?.city ?: ""
            matchesModel.state = match.location?.state ?: ""
            matchesModel.country = match.location?.country ?: ""
            matchesModel.postCode = match.location?.postcode ?: ""
            matchesModel.latitude = match.location?.coordinates?.latitude ?: 0.0
            matchesModel.longitude = match.location?.coordinates?.longitude ?: 0.0
            matchesModel.timeZone = match.location?.timezone?.offset ?: ""
            matchesModel.timeZoneDesc = match.location?.timezone?.description ?: ""
            matchesModel.userName = match.login?.userName ?: ""
            matchesModel.dobDate = match.dob?.date ?: ""
            matchesModel.dobAge = match.dob?.age ?: 0
            matchesModel.regDate = match.registered?.date ?: ""
            matchesModel.regAge = match.registered?.age ?: 0
            matchesModel.phone = match.phone ?: ""
            matchesModel.cell = match.cell ?: ""
            matchesModel.idName = match.id?.name ?: ""
            matchesModel.idValue = match.id?.value ?: ""
            matchesModel.pictureLarge = match.picture?.large ?: ""
            matchesModel.pictureMedium = match.picture?.medium ?: ""
            matchesModel.picturethumbnail = match.picture?.thumbnail ?: ""
            matchesModel.nat = match.nat ?: ""
            matchesModel.dateAdded = Utility.getCurrentTimeStamp()
            arylstMatchesModel.add(matchesModel)
        }
        return arylstMatchesModel
    }

    fun getMatchesByAcceptanceState(acceptanceState: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            matchesList.addAll(
                ArrayList(
                    mainRepository.getMatchesByAcceptanceState(acceptanceState) ?: matchesList
                )
            )
            emit(Resource.success(matchesList))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.error(null, e.message ?: "Error Occured"))
        }

    }

    fun getMatchesByGender(gender: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            matchesList.addAll(ArrayList(mainRepository.getMatchesByGender(gender) ?: matchesList))
            emit(Resource.success(matchesList))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.error(null, e.message ?: "Error Occured"))
        }
    }

    fun getMatchesFromDB(acceptanceGroup: AcceptanceGroup, genderGroup: GenderGroup) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {

                lateinit var acceptanceStates: Array<Int>
                when (acceptanceGroup) {
                    AcceptanceGroup.ACCEPTED -> acceptanceStates = acceptanceGroup.getAccepted()
                    AcceptanceGroup.DECLINED -> acceptanceStates = acceptanceGroup.getDeclined()
                    AcceptanceGroup.NEW -> acceptanceStates = acceptanceGroup.getNew()
                    AcceptanceGroup.ALL -> acceptanceStates = acceptanceGroup.getAll()
                }

                lateinit var genders: Array<String>
                when (genderGroup) {
                    GenderGroup.MALE -> genders = genderGroup.getMale()
                    GenderGroup.FEMALE -> genders = genderGroup.getFemale()
                    GenderGroup.ALL -> genders = genderGroup.getAll()
                }

                matchesList.addAll(
                    ArrayList(
                        mainRepository.getMatches(acceptanceStates, genders) ?: emptyList()
                    )
                )
                Log.d(TAG, "setupObserver:: $matchesList")
                emit(Resource.success(matchesList))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.error(null, e.message ?: "Error Occured"))
            }

        }

    fun updateMatch(matchModel: MatchesModel) {
        viewModelScope.launch {
            mainRepository.updateMatch(matchModel)
        }


    }

}