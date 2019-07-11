package com.example.pingpongscore.gamesetup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.pingpongscore.R
import com.example.pingpongscore.databinding.FragmentGameSetupBinding


class GameSetupFragment : Fragment() {

    private lateinit var binding: FragmentGameSetupBinding

    private val gameStartViewModel: GameSetupViewModel by lazy {
        ViewModelProviders.of(this).get(GameSetupViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Create a binding object for the fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game_setup, container, false
        )

        // Return the root
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.player1Overlay.setOnClickListener {
            Log.v("GameStartFragment", "Player 1 Overlay Clicked ${gameStartViewModel.server.value}")
            gameStartViewModel.setPlayerServing(Server.PLAYER1)
        }

        binding.player2Overlay.setOnClickListener {
            Log.v("GameStartFragment", "Player 2 Overlay Clicked ${gameStartViewModel.server.value}")
            gameStartViewModel.setPlayerServing(Server.PLAYER2)
        }

        binding.startBtn.setOnClickListener {
            val serveVal = gameStartViewModel.server.value?.num ?: 0
            val action = GameSetupFragmentDirections.nextAction(serveVal)
            it.findNavController().navigate(action)
        }

        gameStartViewModel.server.observe(this, Observer {
            when (it) {
                Server.PLAYER1 -> {
                    binding.player1ServeText.visibility = View.VISIBLE
                    binding.player2ServeText.visibility = View.INVISIBLE
                }

                Server.PLAYER2 -> {
                    binding.player1ServeText.visibility = View.INVISIBLE
                    binding.player2ServeText.visibility = View.VISIBLE
                }

                Server.RANDOMPLAYER -> {
                    binding.player1ServeText.visibility = View.INVISIBLE
                    binding.player2ServeText.visibility = View.INVISIBLE
                }
            }
        })
    }


}
