package com.example.pingpongscore.gamematchhistory

import android.service.autofill.FieldClassification
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.pingpongscore.database.Match
import org.w3c.dom.Text

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

