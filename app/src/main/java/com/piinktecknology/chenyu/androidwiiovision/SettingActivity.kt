package com.piinktecknology.chenyu.androidwiiovision

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity

/**
 * Created by chenyu on 20/03/2018.
 */

class SettingActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_setting)

        // Display the fragment as the main content.
        fragmentManager.beginTransaction().replace(android.R.id.content, SettingFragment()).commit()
    }

    fun getSettingEntryFromSharedPreference():SettingEntry{

        val mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        var rootPath = mSharedPreferences.getString("transfer_root",applicationContext.getString(R.string.transfer_root))
        var ip = mSharedPreferences.getString("transfer_ip",applicationContext.getString(R.string.transfer_ip))
        var transferMode = mSharedPreferences.getString("transfer_mode",applicationContext.getString(R.string.transfer_mode))

        var settingEntry = SettingEntry(rootPath, ip, transferMode)
        return settingEntry
    }

    fun getPreferencesFragment():PreferenceFragment{
        return fragmentManager.findFragmentById(android.R.id.content) as PreferenceFragment
    }

    override fun onResume() {
        super.onResume()
        PreferenceManager.getDefaultSharedPreferences(applicationContext).registerOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(applicationContext).unregisterOnSharedPreferenceChangeListener(this)
    }

    //When preference changed set the UI summary
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        val mPreferenceFragment = getPreferencesFragment()
        val changedPreferenced = mPreferenceFragment.findPreference(key)
        changedPreferenced.setSummary(sharedPreferences.getString(key,""))
    }
}
