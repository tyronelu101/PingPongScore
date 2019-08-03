package com.simplu.pingpongscore

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat


class GamePreferenceFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        val enabledTieBreakerPref = preferenceManager.findPreference("tieBreakToggle") as SwitchPreferenceCompat
        enabledTieBreakerPref?.summaryProvider = Preference.SummaryProvider<SwitchPreferenceCompat> {
            if(it.isChecked) {
                "Tie break rule where player has to lead by two points is enabled"
            }
            else {
                "Tie break rule where player has to lead by two points is disabled"
            }
        }

    }
}
