package com.simplu.pingpongscore.gamematchhistory

import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.simplu.pingpongscore.database.Match
import com.simplu.pingpongscore.databinding.MatchItemViewBinding


//Order of methods called
//OnCreateViewHolder, Return ViewHolder, Bind, OnBindViewHolder, Repeat

class MatchAdapter(val longClickListener: MatchListener) : ListAdapter<Match, MatchAdapter.ViewHolder>(MatchDiffCallback()) {

    // Recycler view calls this to display item at the current position.
    // Sets the view holders text using item
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, longClickListener)
    }

    // @parent the recycler view
    // Is called for each item in our list. Creates the view holders for the list but doesn't
    // Set the text which is what onBindViewHolder does
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: MatchItemViewBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object  {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MatchItemViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: Match, longClickListener: MatchListener) {
            binding.match = item
            binding.clickListener = longClickListener

        }
    }
}

class MatchListener(val longClickListener: (matchId: Long) -> Boolean){
    fun onLongClick(match: Match):Boolean = longClickListener(match.matchId)
}

class MatchDiffCallback : DiffUtil.ItemCallback<Match>() {

    // Will check all the fields for equality
    override fun areContentsTheSame(oldItem: Match, newItem: Match): Boolean {
        return oldItem == newItem
    }

    // Returns true if oldItem and newItem has same id
    override fun areItemsTheSame(oldItem: Match, newItem: Match): Boolean {
        return oldItem.matchId == newItem.matchId
    }

}

