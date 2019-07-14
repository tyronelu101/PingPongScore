package com.example.pingpongscore.gamescore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.random.Random

class GameScoreViewModel : ViewModel() {

    private var matchPoint: Int

    private val _player1IsServing = MutableLiveData<Boolean>()
    private val _player1Sets = MutableLiveData<Int>()
    private val _player1Points = MutableLiveData<Int>()

    val player1IsServing: LiveData<Boolean> = _player1IsServing
    val player1Sets: LiveData<Int> = _player1Sets
    val player1Points: LiveData<Int> = _player1Points

    private val _player2IsServing = MutableLiveData<Boolean>()
    private val _player2Sets = MutableLiveData<Int>()
    private val _player2Points = MutableLiveData<Int>()

    val player2IsServing: LiveData<Boolean> = _player2IsServing
    val player2Sets: LiveData<Int> = _player2Sets
    val player2Points: LiveData<Int> = _player2Points

    private var serveCounter: Int
    private var startingServer: Int

    // Stack to keep track of player points, serve, and sets. Used for undo
    /**Think of a more efficient way later**/
    private val player1PointStack = Stack<Int>()
    private val player2PointStack = Stack<Int>()

    private val player1ServeStack = Stack<Boolean>()
    private val player2ServeStack = Stack<Boolean>()

    private val player1SetsStack = Stack<Int>()
    private val player2SetsStack = Stack<Int>()

    private val startingServerStack = Stack<Int>()

    init {
        _player1Points.value = 0
        _player1Sets.value = 0
        _player2Points.value = 0
        _player2Sets.value = 0

        matchPoint = 11
        serveCounter = 0
        startingServer = -1
    }

    // Set the starting player server base on int argument
    fun initializeServers(serverVal: Int) {

        // Player 1 is serving
        if (serverVal == 1) {
            startingServer = 1
            _player1IsServing.value = true
            _player2IsServing.value = false
        }
        // Player 2 is serving
        else if (serverVal == 2) {
            startingServer = 2
            _player1IsServing.value = false
            _player2IsServing.value = true
        }
        // Server value was 0 in which case we randomly choose the starting server
        else {
            val rand = Random.nextInt(1, 3)
            if (rand == 1) {
                startingServer = 1
                _player1IsServing.value = true
                _player2IsServing.value = false
            } else {
                startingServer = 2
                _player1IsServing.value = false
                _player2IsServing.value = true
            }
        }
    }

    fun increasePlayer1Point() {
        record()
        _player1Points.value = _player1Points.value?.plus(1)
        if (_player1Points.value == matchPoint) {
            _player1Sets.value = _player1Sets.value?.plus(1)
            startNewSet()
        } else {
            changeServer()
        }

    }

    fun increasePlayer2Point() {
        record()
        _player2Points.value = _player2Points.value?.plus(1)
        if (_player2Points.value == matchPoint) {
            _player2Sets.value = _player2Sets.value?.plus(1)
            startNewSet()

        } else {
            changeServer()
        }

    }

    //Push values for each player onto a stack
    private fun record() {

        //Push the scores
        val p1Score = _player1Points.value
        val p2Score = _player2Points.value

        //Push the sets
        val p1Sets = _player1Sets.value
        val p2Sets = _player2Sets.value

        //Push the serve status
        val p1IsServe = _player1IsServing.value
        val p2IsServe = _player2IsServing.value

        player1PointStack.push(p1Score)
        player2PointStack.push(p2Score)
        player1SetsStack.push(p1Sets)
        player2SetsStack.push(p2Sets)
        player1ServeStack.push(p1IsServe)
        player2ServeStack.push(p2IsServe)
        startingServerStack.push(startingServer)

    }

    private fun startNewSet() {

        serveCounter = 0
        _player1Points.value = 0
        _player2Points.value = 0

        //Switch the servers
        if (startingServer == 1) {
            startingServer = 2
            _player2IsServing.value = true
            _player1IsServing.value = false
        } else if (startingServer == 2) {
            startingServer = 1
            _player1IsServing.value = true
            _player2IsServing.value = false
        }

    }

    //Changes the current server after every 2 serves
    private fun changeServer() {

        ++serveCounter

        if (_player1IsServing.value!! && (serveCounter % 2 == 0)) {
            _player1IsServing.value = false
            _player2IsServing.value = true
        } else if (_player2IsServing.value!! && (serveCounter % 2 == 0)) {
            _player1IsServing.value = true
            _player2IsServing.value = false
        }
    }

    fun undo() {

        if (serveCounter != 0) {
            --serveCounter
        }

        if (!player1PointStack.empty()) {

            _player1Points.value = player1PointStack.pop()
            _player2Points.value = player2PointStack.pop()
            _player1IsServing.value = player1ServeStack.pop()
            _player2IsServing.value = player2ServeStack.pop()
            _player1Sets.value = player1SetsStack.pop()
            _player2Sets.value = player2SetsStack.pop()
            startingServer = startingServerStack.pop()

        }


    }


}