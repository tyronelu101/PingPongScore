package com.simplu.pingpongscore.gamematchhistory

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.simplu.pingpongscore.database.Match

@BindingAdapter("playerNames")
fun TextView.playerNames(item: Match){
    text = item.player1Name + ":" + item.player2Name
}

@BindingAdapter("playerSets")
fun TextView.setPlayer1SetsWon(item: Match) {
    text = item.player1SetsWon.toString() + "-" + item.player2SetsWon
}

@BindingAdapter("dateAndTimeFinished")
fun TextView.setDateAndTime(item: Match) {
    text = item.dateTimeFinshed
}

