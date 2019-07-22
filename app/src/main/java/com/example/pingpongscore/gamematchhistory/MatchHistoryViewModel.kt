package com.example.pingpongscore.gamematchhistory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.pingpongscore.database.Match
import com.example.pingpongscore.database.MatchDatabaseDao
import kotlinx.coroutines.*

class MatchHistoryViewModel(dataBase: MatchDatabaseDao) : ViewModel() {

    //Note to self. Doesnt require coroutine because room takes care of updating returning livedata for us
    val matches = dataBase.getAllMatches()

    val matchesString = Transformations.map(matches) {
        var stringVal = ""
        for(match in it) {
            stringVal += "${match.player1Name}: ${match.player1SetsWon}, ${match.player2Name}: ${match.player2SetsWon}\n"
        }
        stringVal
    }

}