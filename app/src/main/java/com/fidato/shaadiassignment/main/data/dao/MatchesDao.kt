package com.fidato.shaadiassignment.main.data.dao

import androidx.room.*
import com.fidato.shaadiassignment.model.MatchesModel

@Dao
interface MatchesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun setMatch(match: MatchesModel): Long

    @Update
    suspend fun updateMatch(match: MatchesModel): Int

    @Query("SELECT * FROM matches WHERE isAccepted = :acceptanceState")
    suspend fun getMatchesByAcceptanceState(acceptanceState: Int): List<MatchesModel>

    @Query("SELECT * FROM matches ORDER BY dateAdded DESC")
    suspend fun getAllMatches(): List<MatchesModel>

    @Query("SELECT * FROM matches WHERE gender = :gender ORDER BY dateAdded DESC")
    suspend fun getMatchesByGender(gender: String): List<MatchesModel>

}