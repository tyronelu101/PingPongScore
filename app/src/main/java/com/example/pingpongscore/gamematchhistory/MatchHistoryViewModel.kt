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


}