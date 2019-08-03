package com.simplu.pingpongscore.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MatchDatabaseDao {

    @Insert
    fun insert(match: Match)

    @Query ("SELECT * from match_table WHERE matchId = :key")
    fun get(key: Long): Match?

    @Query("DELETE FROM match_table")
    fun clear()

    @Query("DELETE FROM match_table where matchId = :id")
    fun delete(id: Long)

    @Query("SELECT * FROM match_table ORDER BY date_time_finished DESC")
    fun getAllMatches(): LiveData<List<Match>>


}