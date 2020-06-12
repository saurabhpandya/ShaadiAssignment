package com.fidato.shaadiassignment.main.data.dao

import androidx.room.*
import com.fidato.shaadiassignment.model.MatchesModel

@Dao
interface MatchesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun setMatch(match: MatchesModel): Long

    @Update
    suspend fun updateMatch(match: MatchesModel): Int

    @Query("SELECT * FROM matches WHERE isAccepted = '0'")
    suspend fun getDeclinedMatches(): List<MatchesModel>

    @Query("SELECT * FROM matches WHERE isAccepted = '1'")
    suspend fun getAcceptedMatches(): List<MatchesModel>

    @Query("SELECT * FROM matches ORDER BY dateAdded DESC")
    suspend fun getAllMatches(): List<MatchesModel>

}