package com.example.pingpongscore.gamematchhistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pingpongscore.database.MatchDatabaseDao
import com.example.pingpongscore.gamescore.GameScoreViewModel

class MatchHistoryViewModelFactory(private val dataSource: MatchDatabaseDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MatchHistoryViewModel::class.java)) {
            return MatchHistoryViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")    }

}