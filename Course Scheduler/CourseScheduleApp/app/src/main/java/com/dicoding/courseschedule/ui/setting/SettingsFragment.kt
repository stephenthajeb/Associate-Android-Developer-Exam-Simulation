package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.*
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NightMode

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var dailyReminder : DailyReminder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dailyReminder = DailyReminder()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference


        val themeMode = preferenceManager.findPreference<Preference>(getString(R.string.pref_key_dark)) as ListPreference
        themeMode.setDefaultValue(getString(R.string.pref_dark_auto))

        themeMode.setOnPreferenceChangeListener { _ , newValue ->
            Toast.makeText(context, newValue.toString(), Toast.LENGTH_SHORT).show()

            var mode = NightMode.AUTO
            when (newValue){
                "on" ->  mode = NightMode.ON
                "off"->  mode = NightMode.OFF
                else ->  mode = NightMode.AUTO
            }
            updateTheme(mode.value)
            true
        }

        // Todo: Testing this
        val notifMode = preferenceManager.findPreference<Preference>(getString(R.string.pref_key_notify )) as SwitchPreference
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