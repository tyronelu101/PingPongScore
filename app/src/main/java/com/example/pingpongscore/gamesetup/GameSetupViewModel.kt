package com.example.pingpongscore.gamesetup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameSetupViewModel : ViewModel() {

    // Variable to store the current server
    private val _serve = MutableLiveData<Server>()

    val server: LiveData<Server> = _serve

    init {
        _serve.value = Server.RANDOMPLAYER
        Log.v("GameSetupViewModel", "View model initialized")
    }

    fun setPlayerServing(server: Server) {

        if (_serve.value == server) {
            _serve.value = Server.RANDOMPLAYER
        } else
            _serve.value = server
    }

}



