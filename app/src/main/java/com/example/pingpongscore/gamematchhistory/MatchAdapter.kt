package com.example.pingpongscore.gamematchhistory

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pingpongscore.R
import com.example.pingpongscore.database.Match
import kotlinx.android.synthetic.main.fragment_game_match_history.view.*

class MatchAdapter: RecyclerView.Adapter<MatchItemViewHolder>() {

    var data = listOf<Match>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MatchItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.matchId.toString()
    }

    // @parent the recycler view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.match_item_view, parent, false) as TextView

        return MatchItemViewHolder(view)
    }

}