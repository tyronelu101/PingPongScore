package com.simplu.pingpongscore.gamematchhistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.simplu.pingpongscore.database.MatchDatabaseDao

class MatchHistoryViewModelFactory(private val dataSource: MatchDatabaseDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MatchHistoryViewModel::class.java)) {
            return MatchHistoryViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")    }

}