package com.example.pingpongscore.gamematchhistory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.pingpongscore.database.Match
import com.example.pingpongscore.database.MatchDatabaseDao
import kotlinx.coroutines.*

class MatchHistoryViewModel(val dataBase: MatchDatabaseDao) : ViewModel() {

    //Note to self. Doesnt require coroutine because room takes care of updating returning livedata for us
    val matches = dataBase.getAllMatches()

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun deleteMatch(id: Long) {
        uiScope.launch {
            remove(id)
        }
    }

    private suspend fun remove(id: Long) {
        withContext(Dispatchers.IO) {
            dataBase.delete(id)
        }
    }

}