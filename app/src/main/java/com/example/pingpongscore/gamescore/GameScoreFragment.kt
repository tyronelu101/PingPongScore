package com.example.pingpongscore.gamescore


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.example.pingpongscore.R
import com.example.pingpongscore.database.Match
import com.example.pingpongscore.database.MatchDatabase
import com.example.pingpongscore.databinding.FragmentGameScoreBinding


class GameScoreFragment : Fragment() {

    private lateinit var binding: FragmentGameScoreBinding
    private lateinit var gameScoreViewModel: GameScoreViewModel
    private val args: GameScoreFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_score, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = MatchDatabase.getDatabase(application).matchDao
        val viewModelFactory = GameScoreViewModelFactory(dataSource, application, args.player1Name, args.player2Name)
        gameScoreViewModel = ViewModelProviders.of(this, viewModelFactory).get(GameScoreViewModel::class.java)

        binding.viewModel = gameScoreViewModel
        binding.lifecycleOwner = this

        // Initialize the servers using the arg value
        gameScoreViewModel.initializeServers(args.server)

        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Keep track of initial onDown button press
        var downY = 0f

        binding.root.setOnTouchListener { view, motionEvent ->

            val x = motionEvent.x
            val y = motionEvent.y

            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.options_save -> {
                gameScoreViewModel.saveCurrentMatch()
                Log.v("GameScoreFragment", "Match saved")

            }
            R.id.gameMatchHistoryFragment -> {
                return NavigationUI.onNavDestinationSelected(
                    item!!,
                    view!!.findNavController()
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
