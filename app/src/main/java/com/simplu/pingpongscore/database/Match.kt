package com.simplu.pingpongscore.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/*
Data class to hold data for a single ping pong match
 */

@Entity(tableName = "match_table")
data class Match(

    @PrimaryKey(autoGenerate = true)
    var matchId: Long = 0L,

//    @ColumnInfo(name = "match_end_time")
//    val endTime: Long = System.currentTimeMillis(),
//
//    @ColumnInfo(name = "match_end_date")
//    var endDate: String,

    @ColumnInfo(name = "player1_name")
    var player1Name: String,

    @ColumnInfo(name = "player2_name")
    var player2Name: String,

    @ColumnInfo(name = "player1_sets_won")
    var player1SetsWon: Int,

    @ColumnInfo(name = "player2_sets_won")
    var player2SetsWon: Int,

    @ColumnInfo(name = "date_time_finished")
    var dateTimeFinshed: String

)