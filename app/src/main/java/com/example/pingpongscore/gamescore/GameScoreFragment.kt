package com.example.pingpongscore.gamescore


import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pingpongscore.R
import com.example.pingpongscore.database.MatchDatabase
import com.example.pingpongscore.databinding.FragmentGameScoreBinding

class GameScoreFragment : Fragment(), SaveConfirmDialogFragment.SaveConfirmDialogListener {

    override fun onDialogPositiveClick(dialogFragment: DialogFragment) {
        val saveCode = gameScoreViewModel.saveCurrentMatch()
        if (saveCode == -1) {
            Toast.makeText(
                activity?.applicationContext,
                "Must complete at least one set to save",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                activity?.applicationContext,
                "Match Saved Successfully",
                Toast.LENGTH_SHORT
            ).show()
            val action = GameScoreFragmentDirections.nextAction()
            findNavController().navigate(action)
        }
    }

    override fun onDialogNegativeClick(dialogFragment: DialogFragment) {
        Log.v("DialogFragment", "Cancel clicked")
    }

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
        val viewModelFactory =
            GameScoreViewModelFactory(dataSource, application, args.player1Name, args.player2Name, args.tieBreak)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.score_screen_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item?.itemId) {
            R.id.options_save -> {

                //Display the dialog to confirm save
                val dialog = SaveConfirmDialogFragment()
                dialog.setTargetFragment(this, 0)
                dialog.show(fragmentManager!!, "SaveFragment")
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
