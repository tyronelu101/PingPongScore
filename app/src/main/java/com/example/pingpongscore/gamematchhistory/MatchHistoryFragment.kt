package com.example.pingpongscore.gamematchhistory


import android.os.Bundle
import android.renderscript.ScriptGroup
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.pingpongscore.R
import com.example.pingpongscore.database.MatchDatabase
import com.example.pingpongscore.databinding.FragmentGameMatchHistoryBinding
import com.example.pingpongscore.gamescore.GameScoreViewModel
import com.example.pingpongscore.gamescore.GameScoreViewModelFactory
import java.util.*

class MatchHistoryFragment : Fragment() {

    private lateinit var binding: FragmentGameMatchHistoryBinding
    private lateinit var matchHistoryViewModel: MatchHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_match_history, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = MatchDatabase.getDatabase(application).matchDao
        val viewModelFactory = MatchHistoryViewModelFactory(dataSource)
        matchHistoryViewModel = ViewModelProviders.of(this, viewModelFactory).get(MatchHistoryViewModel::class.java)

        binding.viewModel = matchHistoryViewModel
        binding.lifecycleOwner = this

        val adapter = MatchAdapter()
        binding.matchList.adapter = adapter

        matchHistoryViewModel.matches.observe(this, Observer{
            adapter.data = it
        })

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}
