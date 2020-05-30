package com.phytal.sarona

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.phytal.sarona.fragments.PreferencesFragment

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.fragment_settings)
        super.onCreate(savedInstanceState)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,
                PreferencesFragment()
            )
            .commit()
    }
}
