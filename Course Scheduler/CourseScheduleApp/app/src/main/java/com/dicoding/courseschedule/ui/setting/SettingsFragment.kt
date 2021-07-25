package com.dicoding.courseschedule.ui.setting

import android.content.BroadcastReceiver
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.*
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NightMode
import java.util.*

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference


        val themeMode = preferenceManager.findPreference<Preference>(getString(R.string.pref_key_dark)) as ListPreference
        themeMode.setDefaultValue(getString(R.string.pref_dark_auto))

        themeMode.setOnPreferenceChangeListener { _ , newValue ->
            updateTheme(newValue as Int)
            true
        }

        val dailyReminder = DailyReminder()

        val notifMode = preferenceManager.findPreference<Preference>(getString(R.string.pref_notify_summary)) as SwitchPreference
        notifMode.setOnPreferenceChangeListener { _, newValue ->
            val isActive = newValue as Boolean
            if (isActive) {
                context?.let { dailyReminder.setDailyReminder(it) }
            } else {
                context?.let { dailyReminder.cancelAlarm(it) }
            }
            true
        }
    }


    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}