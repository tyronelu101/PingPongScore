package com.example.pingpongscore.gamematchhistory

import android.service.autofill.FieldClassification
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.pingpongscore.database.Match
import org.w3c.dom.Text

@BindingAdapter("player1Name")
fun TextView.setPlayer1Name(item: Match){
    text = item.player1Name
}

@BindingAdapter("player2Name")
fun TextView.setPlayer2Name(item: Match) {
    text = item.player2Name
}

@BindingAdapter("player1SetsWon")
fun TextView.setPlayer1SetsWon(item: Match) {
    text = item.player1SetsWon.toString()
}

@BindingAdapter("player2SetsWon")
fun TextView.setPlayer2SetsWon(item: Match) {
    text = item.player2SetsWon.toString()
}

@BindingAdapter("dateAndTimeFinished")
fun TextView.setDateAndTime(item: Match) {
    text = item.dateTimeFinshed
}

