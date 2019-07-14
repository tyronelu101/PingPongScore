package com.example.pingpongscore.gamescore


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
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

        var downX = 0f
        var downY = 0f

        binding.root.setOnTouchListener { view, motionEvent ->

            val x = motionEvent.x
            val y = motionEvent.y

            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    downX = motionEvent.x
                    downY = motionEvent.y
                    true
                }

                MotionEvent.ACTION_UP -> {
                    if (x < binding.boundaryView.left && y <= downY) {
                        gameScoreViewModel.increasePlayer1Point()
                    } else if (x > binding.boundaryView.right && y <= downY) {
                        gameScoreViewModel.increasePlayer2Point()
                    } else if (y > downY + 100) {
                        gameScoreViewModel.undo()
                    }

                    true
                }
            }
            true
        }
    }
}
