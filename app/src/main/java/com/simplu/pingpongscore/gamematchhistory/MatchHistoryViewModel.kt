package com.simplu.pingpongscore.gamematchhistory

import androidx.lifecycle.ViewModel
import com.simplu.pingpongscore.database.MatchDatabaseDao
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