package com.simplu.pingpongscore.gamescore

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.simplu.pingpongscore.database.MatchDatabaseDao

class GameScoreViewModelFactory(private val dataSource: MatchDatabaseDao,
                                private val application: Application,
                                private val player1Name: String,
                                private val player2Name: String,
                                private val tieBreaker: Boolean
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GameScoreViewModel::class.java)) {
            return GameScoreViewModel(dataSource, application, player1Name, player2Name, tieBreaker) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}