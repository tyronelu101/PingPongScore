package com.simplu.pingpongscore.gamesetup

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import com.simplu.pingpongscore.R
import com.simplu.pingpongscore.databinding.FragmentGameSetupBinding


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

        setHasOptionsMenu(true)


        // Return the root
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.main_screen_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.gameMatchHistoryFragment -> {
                return NavigationUI.onNavDestinationSelected(
                    item!!, view!!.findNavController()
                )
            }

            R.id.gamePreferenceFragment -> {
                return NavigationUI.onNavDestinationSelected(
                    item!!, view!!.findNavController()
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.player1Overlay.setOnClickListener {
            gameStartViewModel.setPlayerServing(Server.PLAYER1)
        }

        binding.player2Overlay.setOnClickListener {
            gameStartViewModel.setPlayerServing(Server.PLAYER2)
        }

        binding.startBtn.setOnClickListener {
            val serveVal = gameStartViewModel.server.value?.num ?: 0
            val player1Name = binding.player1Text.text.toString()
            val player2Name = binding.player2Text.text.toString()

            // Retrieve the game setting prefernes and pass it into direction args
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity /* Activity context */)
            val tieBreakRule = sharedPreferences.getBoolean("tieBreakToggle", false)

            val action = GameSetupFragmentDirections.nextAction(serveVal, player1Name, player2Name, tieBreakRule)
            it.findNavController().navigate(action)
        }

        binding.player1Text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        val textEditWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.startBtn.isEnabled = !textIsEmpty()
            }

        }

        binding.player1Text.addTextChangedListener(textEditWatcher)
        binding.player2Text.addTextChangedListener(textEditWatcher)

        gameStartViewModel.server.observe(this, Observer {
            when (it) {
                Server.PLAYER1 -> {
                    binding.player1ServeText.visibility = View.VISIBLE
                    binding.player2ServeText.visibility = View.INVISIBLE
                    binding.questionMark.visibility = View.INVISIBLE
                }

                Server.PLAYER2 -> {
                    binding.player1ServeText.visibility = View.INVISIBLE
                    binding.player2ServeText.visibility = View.VISIBLE
                    binding.questionMark.visibility = View.INVISIBLE
                }

                Server.RANDOMPLAYER -> {
                    binding.player1ServeText.visibility = View.INVISIBLE
                    binding.player2ServeText.visibility = View.INVISIBLE
                    binding.questionMark.visibility = View.VISIBLE

                }
            }
        })
    }


    private fun textIsEmpty(): Boolean =
        (TextUtils.isEmpty(binding.player1Text.text) || TextUtils.isEmpty(binding.player2Text.text))
}
