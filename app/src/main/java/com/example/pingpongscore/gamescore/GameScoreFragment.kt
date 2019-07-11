package com.example.pingpongscore.gamescore


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.example.pingpongscore.R
import com.example.pingpongscore.databinding.FragmentGameScoreBinding


class GameScoreFragment : Fragment() {

    private lateinit var binding: FragmentGameScoreBinding

    private val gameScoreViewModel: GameScoreViewModel by lazy {
        ViewModelProviders.of(this).get(GameScoreViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_score, container, false)
        binding.viewModel = gameScoreViewModel
        binding.lifecycleOwner = this

        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: GameScoreFragmentArgs by navArgs()

        // Initialize the servers using the arg value
        gameScoreViewModel.initializeServers(args.server)


    }

}
