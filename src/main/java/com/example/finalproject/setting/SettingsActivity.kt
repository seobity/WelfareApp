package com.example.finalproject.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, SettingsFragment())
            .commit()
    }
}
