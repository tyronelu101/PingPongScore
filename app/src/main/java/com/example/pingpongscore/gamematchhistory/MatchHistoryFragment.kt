package com.example.pingpongscore.gamematchhistory

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.pingpongscore.R
import com.example.pingpongscore.database.MatchDatabase
import com.example.pingpongscore.databinding.FragmentGameMatchHistoryBinding
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager


class MatchHistoryFragment : Fragment() {

    private lateinit var binding: FragmentGameMatchHistoryBinding
    lateinit var matchHistoryViewModel: MatchHistoryViewModel

    // THe match id that we retrieve from the view holder on long click
    private var matchDelID: Long = -1



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game_match_history,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = MatchDatabase.getDatabase(application).matchDao
        val viewModelFactory = MatchHistoryViewModelFactory(dataSource)
        matchHistoryViewModel = ViewModelProviders.of(this, viewModelFactory).get(MatchHistoryViewModel::class.java)

        binding.viewModel = matchHistoryViewModel
        binding.lifecycleOwner = this

        //Create an adapter.
        //Create a long click listener. We retrieve the id of the item that was pressed
        //and store it in matchDelID variable. Return false
        //to propagate listener to onContextMenuCreate where onItemSelected will handle removing
        //item from list
        val adapter = MatchAdapter(MatchListener {
            matchDelID = it
            false
        })

        //Connect the matchadapter to the recycler view
        binding.matchList.adapter = adapter
        registerForContextMenu(binding.matchList)

        val decoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        binding.matchList.addItemDecoration(decoration)

        //Observe the match list in view model. Every time the list changes, set the
        //adapter's match list to the view models list
        matchHistoryViewModel.matches.observe(this, Observer {
            adapter.submitList(it)
        })

        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        val inflater = MenuInflater(v.context)
        inflater.inflate(R.menu.context_menu, menu)
    }
    
    override fun onContextItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.delete -> {
                //Remove the selected match from the database
                matchHistoryViewModel.deleteMatch(matchDelID)
                true
            }
            else -> super.onContextItemSelected(item)
        }

    }
}
